//
// Created by wangjie on 2024/3/28.
//

#ifndef LEARNANDROID_MEMORYFILE_H
#define LEARNANDROID_MEMORYFILE_H


class MemoryFile {
private:
    const char* m_path;
    int m_fd;
    int32_t m_size;
    int8_t *m_ptr;
    int m_actualSize;

    void resize(int32_t needSize);

public:
    MemoryFile(const char *path);

    ~MemoryFile();

    void write(char *data, int dataLen);
};

#endif //LEARNANDROID_MEMORYFILE_H
