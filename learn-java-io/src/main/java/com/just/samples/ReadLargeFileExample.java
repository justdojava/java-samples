package com.just.samples;

import com.google.common.base.Joiner;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * @author andyXu xu9529@gmail.com
 * @date 2020/6/9
 * 读取超大文件
 */
public class ReadLargeFileExample {


    @SneakyThrows
    public static void main(String[] args) {
        readInMemory();
    }

    /**
     * 全部读进内存
     */
    @SneakyThrows
    public static void readInMemory() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        List<String> lines = FileUtils.readLines(new File("temp/test.txt"), Charset.defaultCharset());
        for (String line : lines) {
            // pass
        }
        stopwatch.stop();
        System.out.println("read all lines spend " + stopwatch.elapsed(TimeUnit.SECONDS) + " s");
        logMemory();
    }

    public static void readInBuffer() {

        try (BufferedReader fileBufferReader = new BufferedReader(new FileReader("temp/test.txt"))) {
            String fileLineContent;
            while ((fileLineContent = fileBufferReader.readLine()) != null) {
                // process the line.
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @SneakyThrows
    public static void readInApacheIO() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        LineIterator fileContents = FileUtils.lineIterator(new File("temp/test.txt"), StandardCharsets.UTF_8.name());
        while (fileContents.hasNext()) {
            fileContents.nextLine();
            //  pass
        }
        logMemory();
        fileContents.close();
        stopwatch.stop();
        System.out.println("read all lines spend " + stopwatch.elapsed(TimeUnit.SECONDS) + " s");
    }

    @SneakyThrows
    public static void readInStream() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        // lines(Path path, Charset cs)
        try (Stream<String> inputStream = Files.lines(Paths.get("temp/test.txt"), StandardCharsets.UTF_8)) {
            inputStream
                    .filter(str -> str.length() > 5)// 过滤数据
                    .forEach(o -> {
                        // pass do sample logic
                    });
        }
        logMemory();
        stopwatch.stop();
        System.out.println("read all lines spend " + stopwatch.elapsed(TimeUnit.SECONDS) + " s");
    }

    @SneakyThrows
    public static void readInApacheIOWithThreadPool() {
        // 创建一个 最大线程数为 10，队列最大数为 100 的线程池
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 60l, TimeUnit.SECONDS, new LinkedBlockingDeque<>(100));
        // 使用 Apache 的方式逐行读取数据
        LineIterator fileContents = FileUtils.lineIterator(new File("temp/test.txt"), StandardCharsets.UTF_8.name());
        List<String> lines = Lists.newArrayList();
        while (fileContents.hasNext()) {
            String nextLine = fileContents.nextLine();
            lines.add(nextLine);
            // 读取到十万的时候
            if (lines.size() == 100000) {
                // 拆分成两个 50000 ，交给异步线程处理
                List<List<String>> partition = Lists.partition(lines, 50000);
                List<Future> futureList = Lists.newArrayList();
                for (List<String> strings : partition) {
                    Future<?> future = threadPoolExecutor.submit(() -> {
                        processTask(strings);
                    });
                    futureList.add(future);
                }
                // 等待两个线程将任务执行结束之后，再次读取数据。这样的目的防止，任务过多，加载的数据过多，导致 OOM
                for (Future future : futureList) {
                    // 等待执行结束
                    future.get();
                }
                // 清除内容
                lines.clear();
            }

        }
        // lines 若还有剩余，继续执行结束
        if (!lines.isEmpty()) {
            // 继续执行
            processTask(lines);
        }
    }

    public static void splitFileAndRead() throws Exception {
        // 先将大文件拆分成小文件
        List<File> fileList = splitLargeFile("temp/test.txt");
        // 创建一个 最大线程数为 10，队列最大数为 100 的线程池
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 60l, TimeUnit.SECONDS, new LinkedBlockingDeque<>(100));
        List<Future> futureList = Lists.newArrayList();
        for (File file : fileList) {
            Future<?> future = threadPoolExecutor.submit(() -> {
                try (Stream inputStream = Files.lines(file.toPath(), StandardCharsets.UTF_8)) {
                    inputStream.forEach(o -> {
                        // 模拟执行业务
                        try {
                            TimeUnit.MILLISECONDS.sleep(10L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            futureList.add(future);
        }
        for (Future future : futureList) {
            // 等待所有任务执行结束
            future.get();
        }
        threadPoolExecutor.shutdown();


    }

    private static List<File> splitLargeFile(String largeFileName) throws IOException {
        LineIterator fileContents = FileUtils.lineIterator(new File(largeFileName), StandardCharsets.UTF_8.name());
        List<String> lines = Lists.newArrayList();
        // 文件序号
        int num = 1;
        List<File> files = Lists.newArrayList();
        while (fileContents.hasNext()) {
            String nextLine = fileContents.nextLine();
            lines.add(nextLine);
            // 每个文件 10w 行数据
            if (lines.size() == 100000) {
                createSmallFile(lines, num, files);
                num++;
            }
        }
        // lines 若还有剩余，继续执行结束
        if (!lines.isEmpty()) {
            // 继续执行
            createSmallFile(lines, num, files);
        }
        return files;
    }

    public static void createSmallFile(List<String> lines, int num, List<File> files) throws IOException {
        File file = new File("temp/small_file_" + num + ".txt");
        FileUtils.writeLines(file, lines);
        files.add(file);
        lines.clear();
    }


    private static void processTask(List<String> strings) {
        for (String line : strings) {
            // 模拟业务执行
            try {
                TimeUnit.MILLISECONDS.sleep(10L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private static void logMemory() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        //椎内存使用情况
        MemoryUsage memoryUsage = memoryMXBean.getHeapMemoryUsage();
        //初始的总内存
        long totalMemorySize = memoryUsage.getInit();
        //已使用的内存
        long usedMemorySize = memoryUsage.getUsed();

        System.out.println("Total Memory: " + totalMemorySize / (1024 * 1024) + " Mb");
        System.out.println("Used Memory: " + usedMemorySize / (1024 * 1024) + " Mb");
    }


    /**
     * 创建一个超大的文件
     */
    @SneakyThrows
    public static void createLargeFile() {
        List<String> fields = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            fields.add(UUID.randomUUID().toString());
        }
        String line = Joiner.on(",").join(fields);

        List<String> lines = Lists.newArrayList();
        for (int i = 0; i < 200 * 10000; i++) {
            lines.add(line);
            // 防止 OOM，批量输出
            if (lines.size() == 100000) {
                FileUtils.writeLines(new File("temp/test.txt"), lines, true);
                lines.clear();
            }
        }


    }

}
