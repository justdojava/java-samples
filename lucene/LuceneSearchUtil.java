import org.apache.commons.collections.MapUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;


public class LuceneSearchUtil {

    private static String INDEX_PATH = "/opt/lucene/demo";
    private static IndexSearcher searcher;

    public static LuceneSearchUtil getInstance() {
        return LuceneSearchUtil.SingletonHolder.searchUtil;
    }

    private static class SingletonHolder {
        public final static LuceneSearchUtil searchUtil = new LuceneSearchUtil();
    }

    private LuceneSearchUtil() {
        this.initSearcher();
    }

    private void initSearcher() {
        Directory directory;
        try {
            directory = FSDirectory.open(Paths.get(INDEX_PATH));
            DirectoryReader reader = DirectoryReader.open(directory);
            searcher = new IndexSearcher(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TopDocs searchByMap(Map<String, Object> queryMap) throws Exception {
        if (null == searcher) {
            this.initSearcher();
        }
        if (MapUtils.isNotEmpty(queryMap)) {
            BooleanQuery.Builder builder = new BooleanQuery.Builder();
            queryMap.forEach((key, value) -> {
                if (value instanceof String) {
                    Query queryString = new PhraseQuery(key, (String) value);
//                    Query queryString = new TermQuery(new Term(key, (String) value));
                    builder.add(queryString, BooleanClause.Occur.MUST);
                }
            });
            return searcher.search(builder.build(), 10);
        }
        return null;
    }

}
