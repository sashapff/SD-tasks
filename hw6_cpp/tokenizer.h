//
// Created by Alexandra Ivanova on 11.12.2021.
//

#pragma once

#include <vector>

class Token {
};

class Open : public Token {
};

class Close : public Token {
};

class Number : public Token {
public:
    explicit Number(int64_t value) : value_(value) {}

    int64_t GetValue() const {
        return value_;
    }

private:
    int64_t value_;
};

class Add : public Token {
};

class Sub : public Token {
};

class Mul : public Token {
};

class Div : public Token {
};

class End : public Token {
};

class Tokenizer {
public:
    Tokenizer(std::istream *in) : in_(in) {
    }

    std::vector<std::shared_ptr<Token>> ReadInput() {
        std::vector<std::shared_ptr<Token>> tokens;
        auto token = NextToken();
        while (token) {
            tokens.push_back(token);
            token = NextToken();
        }
        return tokens;
    }

private:
    std::istream *in_;

    std::shared_ptr<Token> NextToken() {
        char current_symbol = in_->get();
        while (current_symbol == ' ') {
            current_symbol = in_->get();
        }
        if (current_symbol == '+') {
            return std::make_shared<Add>();
        } else if (current_symbol == '-') {
            return std::make_shared<Sub>();
        } else if (current_symbol == '*') {
            return std::make_shared<Mul>();
        } else if (current_symbol == '/') {
            return std::make_shared<Div>();
        } else if (current_symbol >= '0' && current_symbol <= '9') {
            int64_t number_value = current_symbol - '0';
            char next = in_->peek();
            while (next >= '0' && next <= '9') {
                number_value *= 10;
                current_symbol = in_->get();
                number_value += (current_symbol - '0');
                next = in_->peek();
            }
            return std::make_shared<Number>(number_value);
        } else if (current_symbol == '(') {
            return std::make_shared<Open>();
        } else if (current_symbol == ')') {
            return std::make_shared<Close>();
        } else if (current_symbol == EOF) {
            return nullptr;
        } else {
            throw std::runtime_error(&"Unexpected symbol: "[current_symbol]);
        }
    }
};

