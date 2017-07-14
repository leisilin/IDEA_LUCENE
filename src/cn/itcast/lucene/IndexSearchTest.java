package cn.itcast.lucene;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/14.
 */
public class IndexSearchTest {


    @Test
    public void testIndexCreate() throws Exception{

        //采集系统中的文档, 放入lucene中
        //文档列表存储文档对象
        List<Document> doList = new ArrayList<>();

        //指定文件所在路径
        File dir = new File("H:\\渔父\\2016年6月\\【阶段14】Lucence\\lucene_day01\\参考资料\\searchsource");
        for(File file:dir.listFiles()){
            //文件名称
            String fileName = file.getName();
            //文件内容
            String fileContent = FileUtils.readFileToString(file);
            //文件大小
            Long fileSize = FileUtils.sizeOf(file);

            //文档对象, lucene文件系统中另一个文件就是document对象
            Document document = new Document();

            //设置参数1. 域名
            //设置参数2, 域值
            //设置参数3, 是否存储 yes  or   no
            TextField nameField = new TextField("fileName",fileName, Field.Store.YES);
            TextField contentField = new TextField("fileContent",fileContent, Field.Store.YES);
            TextField sizeField = new TextField("fileSize",fileSize.toString(), Field.Store.YES);

            //将所有的域存到文档中
            document.add(nameField);
            document.add(contentField);
            document.add(sizeField);

            //文档存到集合中
            doList.add(document);
        }

        // 创建分词器
        Analyzer analyzer = new StandardAnalyzer();

        //指定文档和索引存入的地方
        Directory directory = FSDirectory.open(new File("d:/download"));
        //创建些对象初始化对象
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_4_10_3,analyzer);
        // 创建索引和文档写对象
        IndexWriter indexWriter = new IndexWriter(directory,indexWriterConfig);

        // 将文档加入到索引和写对象中
        for(Document fields:doList){
            indexWriter.addDocument(fields);
        }
        indexWriter.commit();
        indexWriter.close();
    }
}
