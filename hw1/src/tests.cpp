#include "LRUCache.h"
#include <gtest/gtest.h>
#include <random>

TEST(SetAndGet, LRUCache) {
    LRUCache cache(10);

    std::string value;

    cache.Set("a", "1");
    cache.Set("b", "2");
    cache.Set("c", "3");

    ASSERT_TRUE(cache.Get("a", &value));
    ASSERT_EQ("1", value);
    ASSERT_TRUE(cache.Get("b", &value));
    ASSERT_EQ("2", value);
    ASSERT_TRUE(cache.Get("c", &value));
    ASSERT_EQ("3", value);

    ASSERT_FALSE(cache.Get("d", &value));

    cache.Set("c", "4");
    ASSERT_TRUE(cache.Get("c", &value));
    ASSERT_EQ("4", value);
}

TEST(Eviction, LRUCache) {
    LRUCache cache(2);
    std::string value;

    cache.Set("a", "1");
    cache.Set("b", "2");
    cache.Set("c", "3");

    ASSERT_FALSE(cache.Get("a", &value));
    ASSERT_TRUE(cache.Get("b", &value));
    ASSERT_TRUE(cache.Get("c", &value));

    cache.Set("b", "4");
    cache.Set("c", "5");
    cache.Set("b", "6");

    cache.Set("e", "7");
    ASSERT_FALSE(cache.Get("c", &value));
    ASSERT_TRUE(cache.Get("b", &value));
    ASSERT_TRUE(cache.Get("e", &value));

    cache.Get("b", &value);
    cache.Set("f", "8");
    ASSERT_FALSE(cache.Get("e", &value));
    ASSERT_TRUE(cache.Get("b", &value));
    ASSERT_TRUE(cache.Get("f", &value));
}

std::mt19937 gen;
int generateInt() {
    std::uniform_int_distribution<int> dist(std::numeric_limits<int>::min(), std::numeric_limits<int>::max());
    return dist(gen);
}

TEST(Stress, LruCache) {
    LRUCache cache(100);
    std::string value;

    for (size_t i = 0; i < 100000; ++i) {

        if (generateInt() % 2 == 0) {
            cache.Set(std::to_string(generateInt() % 500), "foo");
        } else {
            cache.Get(std::to_string(generateInt() % 500), &value);
        }
    }
}
