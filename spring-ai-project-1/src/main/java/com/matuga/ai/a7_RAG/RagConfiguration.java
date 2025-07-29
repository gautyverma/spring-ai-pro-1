package com.matuga.ai.a7_RAG;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class RagConfiguration {

  private static final Logger log = LoggerFactory.getLogger(RagConfiguration.class);

  private final String vectorStoreName = "vectorstore.json";

  @Value("classpath:/data/models.json")
  private Resource models;
  // commenting cause of  Current google-api not able to configure vectorStore so not initializing it.
//  @Bean
  SimpleVectorStore simpleVectorStore(EmbeddingModel embeddingModel) {
    var simpleVectorStore = SimpleVectorStore.builder(embeddingModel).build();
    var vectorStoreFile = getVectorStoreFile();
    if (vectorStoreFile.exists()) {
      log.info("Vector Store File Exists,");
      simpleVectorStore.load(vectorStoreFile);
    } else {
      log.info("Vector Store File Does Not Exist, loading documents");
      TextReader textReader = new TextReader(models);
      textReader.getCustomMetadata().put("filename", "models.txt");
      List<Document> documents = textReader.get();
      TextSplitter textSplitter = new TokenTextSplitter();
      List<Document> splitDocuments = textSplitter.apply(documents);
      simpleVectorStore.add(splitDocuments);
      simpleVectorStore.save(vectorStoreFile);
    }
    return simpleVectorStore;
  }

  private File getVectorStoreFile() {
    Path path = Paths.get("src", "main", "resources", "data");
    String absolutePath = path.toFile().getAbsolutePath() + "/" + vectorStoreName;
    return new File(absolutePath);
  }
}
