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
    explicit LRUCache(size_t max_size) : max_size_(max_size) {
        assert(max_size_ > 0);
    }

    void Set(const std::string &key, const std::string &value) {
        assert(size_ <= max_size_);
        assert(map_.size() <= max_size_);
        if (map_.find(key) != map_.end()) {
            RemoveKey(key);
            AddKey(key, value);
        } else {
            if (size_ == max_size_) {
                DropLast();
            }
            AddKey(key, value);
        }
        assert(size_ <= max_size_);
        assert(map_.size() <= max_size_);
    }

    bool Get(const std::string &key, std::string *value) {
        assert(size_ <= max_size_);
        assert(map_.size() <= max_size_);
        if (map_.find(key) != map_.end()) {
            *value = map_[key];
            RemoveKey(key);
            AddKey(key, *value);
            assert(size_ <= max_size_);
            assert(map_.size() <= max_size_);
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
        assert(size_ == max_size_);
        assert(map_.size() == max_size_);

        std::string key = list_.front();
        map_.erase(key);
        pointers_.erase(key);
        list_.pop_front();
        size_--;

        assert(map_.find(key) == map_.end());
        assert(pointers_.find(key) == pointers_.end());
        assert(size_ + 1 == max_size_);
        assert(map_.size() + 1 == max_size_);
    }

    void AddKey(const std::string &key, const std::string &value) {
        assert(size_ < max_size_);
        assert(map_.size() < max_size_);

        map_[key] = value;
        list_.push_back(key);
        pointers_[key] = list_.end();
        --pointers_[key];
        size_++;

        assert(size_ <= max_size_);
        assert(map_.size() <= max_size_);
        auto last = --list_.end();
        assert(*last == key);
        assert(map_[*last] == value);
        assert(map_.find(key)->second == value);
        assert(pointers_.find(key) != pointers_.end());
    }

    void RemoveKey(const std::string &key) {
        assert(size_ <= max_size_);
        assert(map_.size() <= max_size_);

        auto position = pointers_[key];
        list_.erase(position);
        pointers_.erase(key);
        map_.erase(key);
        size_--;

        assert(size_ < max_size_);
        assert(map_.size() < max_size_);
        assert(map_.find(key) == map_.end());
        assert(pointers_.find(key) == pointers_.end());

    }
};

#endif //SD_LRUCACHE_H
