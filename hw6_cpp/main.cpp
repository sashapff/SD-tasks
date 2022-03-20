#include <iostream>
#include "parse_visitor.h"
#include "tokenizer.h"


int main() {
    std::vector<std::shared_ptr<Token>> tokens = Tokenizer(&std::cin).ReadInput();
    std::vector<std::shared_ptr<Token>> parsed_tokens = Parser(tokens).Parse();
    auto expr = ParseExpression();
    std::cout << expr->Evaluate();
    return 0;
}
