package com.study.springbatch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.AfterChunkError;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.scope.context.ChunkContext;

@Slf4j
public class CustomChunkListener {

    @BeforeChunk
    public void beforeChunk(ChunkContext chunkContext) {
        log.info(">> before Chunk");
    }

    @AfterChunk
    public void afterChunk(ChunkContext chunkContext) {
        log.info(">> after Chunk");
    }

    @AfterChunkError
    public void afterChunkError(ChunkContext chunkContext) {
        log.info(">> after Chunk Error");
    }

}
