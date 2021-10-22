//
// Created by Aleksandra Ivanova on 22.10.2021.
//

#ifndef SD_LRUCACHE_H
#define SD_LRUCACHE_H

#include <string>
#include <list>
#include <unordered_map>

class LRUCache {
public:
    LRUCache(size_t max_size) : max_size_(max_size) {
    }

    void Set(const std::string &key, const std::string &value) {
        if (map_.find(key) != map_.end()) {
            UpdatePosition(key);
            Add(key, value);
            return;
        }
        if (size_ == max_size_) {
            DropLast();
        }
        Add(key, value);
        size_++;
    }

    bool Get(const std::string &key, std::string *value) {
        if (map_.find(key) != map_.end()) {
            *value = map_[key];
            UpdatePosition(key);
            Add(key, *value);
            return true;
        }
        return false;
    }

private:
    int size_ = 0;
    int max_size_;
    std::unordered_map<std::string, std::string> map_;
    std::unordered_map<std::string, std::list<std::string>::iterator> pointers_;
    std::list<std::string> list_;

    void DropLast() {
        std::string key = list_.front();
        map_.erase(key);
        pointers_.erase(key);
        list_.pop_front();
        size_--;
    }

    void Add(const std::string &key, const std::string &value) {
        map_[key] = value;
        list_.push_back(key);
        pointers_[key] = list_.end();
        --pointers_[key];
    }

    void UpdatePosition(const std::string &key) {
        auto position = pointers_[key];
        list_.erase(position);
    }
};

#endif //SD_LRUCACHE_H
