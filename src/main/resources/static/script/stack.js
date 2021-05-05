/*Implement Stack For UI */

class Stack {
    constructor() {
        this.stack = [];
    }

    isEmpty() {
        return this.stack.length == 0;
    }

    push(element) {
        this.stack.push(element);
    }

    pop() {
        if (this.stack.length == 0) {
            return "Underflow";
        } else {
            return this.stack.pop();
        }
    }

    peek() {
        return this.stack[this.stack.length - 1];
    }

    printStack() {
        var string = "";
        for (var i = 0; i < this.stack.length; i++) {
            string += this.stack[i] + " ";
        }
        return string;
    }
}
