import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LuceneIndexUtil {

    private static String INDEX_PATH = "/opt/lucene/demo";
    private static IndexWriter writer;

    public static LuceneIndexUtil getInstance() {
        return SingletonHolder.luceneUtil;
    }

    private static class SingletonHolder {
        public final static LuceneIndexUtil luceneUtil = new LuceneIndexUtil();
    }

    private LuceneIndexUtil() {
        this.initLuceneUtil();
    }

    private void initLuceneUtil() {
        try {
            Directory dir = FSDirectory.open(Paths.get(INDEX_PATH));
            Analyzer analyzer = new StandardAnalyzer();
            IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
            writer = new IndexWriter(dir, iwc);
        } catch (IOException e) {
            log.error("create luceneUtil error");
            if (null != writer) {
                try {
                    writer.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } finally {
                    writer = null;
                }
            }
        }
    }

    /**
     * 索引单个文档
     *
     * @param doc 文档信息
     * @throws IOException IO 异常
     */
    public void addDoc(Document doc) throws IOException {
        if (null != doc) {
            writer.addDocument(doc);
            writer.commit();
            writer.close();
        }
    }

    /**
     * 索引单个实体
     *
     * @param model 单个实体
     * @throws IOException IO 异常
     */
    public void addModelDoc(Object model) throws IOException {
        Document document = new Document();
        List<Field> fields = luceneField(model.getClass());
        fields.forEach(document::add);
        writer.addDocument(document);
        writer.commit();
        writer.close();
    }

    /**
     * 索引实体列表
     *
     * @param objects 实例列表
     * @throws IOException IO 异常
     */
    public void addModelDocs(List<?> objects) throws IOException {
        if (CollectionUtils.isNotEmpty(objects)) {
            List<Document> docs = new ArrayList<>();
            objects.forEach(o -> {
                Document document = new Document();
                List<Field> fields = luceneField(o);
                fields.forEach(document::add);
                docs.add(document);
            });
            writer.addDocuments(docs);
        }
    }

    /**
     * 清除所有文档
     *
     * @throws IOException IO 异常
     */
    public void delAllDocs() throws IOException {
        writer.deleteAll();
    }

    /**
     * 索引文档列表
     *
     * @param docs 文档列表
     * @throws IOException IO 异常
     */
    public void addDocs(List<Document> docs) throws IOException {
        if (CollectionUtils.isNotEmpty(docs)) {
            long startTime = System.currentTimeMillis();
            writer.addDocuments(docs);
            writer.commit();
            log.info("共索引{}个 Document，共耗时{} 毫秒", docs.size(), (System.currentTimeMillis() - startTime));
        } else {
            log.warn("索引列表为空");
        }
    }

    /**
     * 根据实体 class 对象获取字段类型，进行 lucene Field 字段映射
     *
     * @param modelObj 实体 modelObj 对象
     * @return 字段映射列表
     */
    public List<Field> luceneField(Object modelObj) {
        Map<String, Object> classFields = ReflectionUtils.getClassFields(modelObj.getClass());
        Map<String, Object> classFieldsValues = ReflectionUtils.getClassFieldsValues(modelObj);

        List<Field> fields = new ArrayList<>();
        for (String key : classFields.keySet()) {
            Field field;
            String dataType = StringUtils.substringAfterLast(classFields.get(key).toString(), ".");
            switch (dataType) {
                case "Integer":
                    field = new IntPoint(key, (Integer) classFieldsValues.get(key));
                    break;
                case "Long":
                    field = new LongPoint(key, (Long) classFieldsValues.get(key));
                    break;
                case "Float":
                    field = new FloatPoint(key, (Float) classFieldsValues.get(key));
                    break;
                case "Double":
                    field = new DoublePoint(key, (Double) classFieldsValues.get(key));
                    break;
                case "String":
                    String string = (String) classFieldsValues.get(key);
                    if (StringUtils.isNotBlank(string)) {
                        if (string.length() <= 1024) {
                            field = new StringField(key, (String) classFieldsValues.get(key), Field.Store.YES);
                        } else {
                            field = new TextField(key, (String) classFieldsValues.get(key), Field.Store.NO);
                        }
                    } else {
                        field = new StringField(key, StringUtils.EMPTY, Field.Store.NO);
                    }
                    break;
                default:
                    field = new TextField(key, JsonUtils.obj2Json(classFieldsValues.get(key)), Field.Store.YES);
                    break;
            }
            fields.add(field);
        }
        return fields;
    }
    public void close() {
        if (null != writer) {
            try {
                writer.close();
            } catch (IOException e) {
                log.error("close writer error");
            }
            writer = null;
        }
    }

    public void commit() throws IOException {
        if (null != writer) {
            writer.commit();
            writer.close();
        }
    }
}


