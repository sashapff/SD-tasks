//
// Created by Alexandra Ivanova on 11.12.2021.
//

#include "tokenizer.h"

#pragma once

template <class T>
std::shared_ptr<T> As(const std::shared_ptr<Token>& obj) {
    T* result = dynamic_cast<T*>(obj.get());
    return std::make_shared<T>(*result);
}

template <class T>
bool Is(const std::shared_ptr<Token>& obj) {
    return dynamic_cast<T*>(obj.get());
}

class Parser {
public:
    Parser(const std::vector<std::shared_ptr<Token>> &tokens) : tokens_(tokens) {}

    std::vector<std::shared_ptr<Token>> Parse() {
        std::vector<std::shared_ptr<Token>> parsed_tokens;

        return parsed_tokens;
    }

private:
    std::vector<std::shared_ptr<Token>> tokens_;

    int pos_ = 0;

};





