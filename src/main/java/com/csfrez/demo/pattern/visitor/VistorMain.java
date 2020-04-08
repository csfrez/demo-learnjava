package com.csfrez.demo.pattern.visitor;

import java.io.File;

public class VistorMain {

    public static void main(String[] args) {
        FileStructure fs = new FileStructure(new File("."));
        fs.handle(new JavaFileVisitor());
    }
}
