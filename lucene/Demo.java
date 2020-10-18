import java.util.ArrayList;
import java.util.List;

/**
 * <br>
 * <b>Function：</b><br>
 * <b>Author：</b>@author Silence<br>
 * <b>Date：</b>2020-10-17 21:08<br>
 * <b>Desc：</b>无<br>
 */
public class Demo {
    public static void main(String[] args) {
        LuceneIndexUtil luceneUtil = LuceneIndexUtil.getInstance();
        List<ArticleModel> articles = new ArrayList<>();
        try {
            //索引数据
            ArticleModel article1 = new ArticleModel();
            article1.setTitle("Java 极客技术");
            article1.setAuthor("鸭血粉丝");
            article1.setContent("这是一篇给大家介绍 Lucene 的技术文章，必定点赞评论转发！！！");
            ArticleModel article2 = new ArticleModel();
            article2.setTitle("极客技术");
            article2.setAuthor("鸭血粉丝");
            article2.setContent("此处省略两千字...");
            ArticleModel article3 = new ArticleModel();
            article3.setTitle("Java 极客技术");
            article3.setAuthor("鸭血粉丝");
            article3.setContent("最后邀请你加入我们的知识星球，Today is big day!");
            articles.add(article1);
            articles.add(article2);
            articles.add(article3);
            luceneUtil.addModelDocs(articles);
            luceneUtil.commit();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
