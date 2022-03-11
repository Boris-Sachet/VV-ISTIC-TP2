package fr.istic.vv;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.visitor.VoidVisitorWithDefaults;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrivateElementsWithoutGetterPrinter extends VoidVisitorWithDefaults<Void> {
    private final String resultPath;
    public PrivateElementsWithoutGetterPrinter(String path) {
        this.resultPath =path;
    }

    @Override
    public void visit(CompilationUnit unit, Void arg) {
        for(TypeDeclaration<?> type : unit.getTypes()) {
            type.accept(this, null);
        }
    }

    public void visitTypeDeclaration(TypeDeclaration<?> declaration, Void arg) {
        // Create result file
        String fileName = resultPath +"result.txt";
        try {
            File file = new File(fileName);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists");
            }
        } catch (IOException e) {
            System.out.println("Error bitch");
            e.printStackTrace();
        }

        if(!declaration.isPublic()) return;
        List<String> variableList = new ArrayList<>();
        List<String> issuesVariable = new ArrayList<>();
        // Parse all the fields to find the private ones
        for(FieldDeclaration var : declaration.getFields()){
            if(var.isPrivate()){
                variableList.add(var.getVariable(0).getName().toString());
            }
        }
        if(!variableList.isEmpty()){
            issuesVariable = variableList;
            System.out.println("Looking for privates fields without getters : ");
            for(String varName : variableList){
                for(MethodDeclaration method : declaration.getMethods()){
                    if (method.getNameAsString().equalsIgnoreCase("get"+varName)){
                        issuesVariable.remove(varName);
                    }
                }
            }
        }
        if(!issuesVariable.isEmpty()){
            try {
                FileWriter myWriter = new FileWriter(fileName);
                myWriter.write("The code analysis revealed " + issuesVariable.size() +" privates instances " +
                        "variables without getters such as : \n");
                for(String result : issuesVariable){
                    myWriter.write(result + "\n");
                }
                myWriter.close();
                System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration declaration, Void arg) {
        visitTypeDeclaration(declaration, arg);
    }

    @Override
    public void visit(EnumDeclaration declaration, Void arg) {
        visitTypeDeclaration(declaration, arg);
    }

    @Override
    public void visit(MethodDeclaration declaration, Void arg) {
        if(!declaration.isPublic()) return;
    }
}