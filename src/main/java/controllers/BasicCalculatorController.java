package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utilities.BasicFXUtilities;

import java.net.URL;
import java.util.LinkedList;
import java.util.Objects;
import java.util.ResourceBundle;

@Component
public class BasicCalculatorController implements Initializable{

    private Stage primaryStage;

    /*Variable declaration*/

    //This list will help us to keep track of the current number we are entering and want to use in calculating
    private LinkedList<String> buffer = new LinkedList<>() ;

    //Will be used to store the current operation we are performing
    private String operation ;

    /*This string will be used to keep track of the last buffer*/
    private String lastBuffer ;

    //Will be used to the result from the previous operation
    private String currentResult = "" ;

    /*This list will help us keep track of all the operations we are about to perform*/
    private LinkedList <String> operationLists = new LinkedList<>() ;



    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        primaryStage.setTitle("Calculator - Basic");
        primaryStage.setResizable(false);
    }

    @Autowired
    public void setPrimaryStage (Stage primaryStage) {
        this.primaryStage = primaryStage ;
    }

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="answerArea"
    private TextField answerArea; // Value injected by FXMLLoader

    @FXML // fx:id="operationsArea"
    private TextField operationsArea; // Value injected by FXMLLoader

    @FXML
    void addToMemory(ActionEvent event) {

    }

    @FXML
    void appendNegative(ActionEvent event) {

        if (BasicFXUtilities.getCurrentResult() != null){
            if (!BasicFXUtilities.getCurrentResult().isEmpty()) {
                if (buffer.size() > 0) {
                    answerArea.insertText(0,"-");
                    buffer.addFirst(((Button) event.getSource()).getText());
                } else {
                    System.out.println("Buffer is zero...====");
                    System.out.println("Display the operations list: " + operationLists);

                    answerArea.insertText(0,"-");
                    buffer.addFirst(((Button) event.getSource()).getText());
                    buffer.add(BasicFXUtilities.getCurrentResult());

                    System.out.println("Now the current result is: " + BasicFXUtilities.getCurrentResult());
                    System.out.println("The buffer now is: " + buffer);
                }
            } else {
                System.out.println("Current result is zero===========");
                System.out.println("Display current operations list again: " + operationLists);
            }
        } else {
            if (buffer.size() > 0){
                if (Objects.equals(buffer.getFirst(), "±")){
                    System.out.println("negative already exist");
                } else {
                    answerArea.insertText(0,"-");
                    buffer.addFirst(((Button) event.getSource()).getText());
                }
            } else {
                System.out.println("It is null and buffer is zero");
            }
        }
    }

    @FXML
    void calculate(ActionEvent event) {
        displayResultsFromEqualButton();
    }

    @FXML
    void clearAnswerAndOperationArea(ActionEvent event) {

    }

    @FXML
    void clearAnswerArea(ActionEvent event) {

    }

    @FXML
    void clearMemory(ActionEvent event) {

    }

    @FXML
    void performAddition(ActionEvent event) {

        displayResults(event);


        /*String firstNumber = "", secondNumber = "" ;

        if (operationLists.size() > 0){
            *//*If we have done some operations before. What ever the operations are. We have already have something like this
            * n+
            * It means we are pressing the operations for a second time. This might mean that we want
            * to add what is in the operations list (First number) and what we currently have in the buffer (Second number)*//*

            if (BasicFXUtilities.checkLastItemInOperationList(operationLists)){
                *//*If operations list is not empty, it means we either have something like
                * n in the list or n+ or n- et cetera in the list
                * So we check if the buffer is empty or not*//*

                if (buffer.size() == 0){
                    *//*Buffer is zero in this case will mean that, we have something like
                    * n+ or n- or n* or n/ et cetera and we try to press a + again.
                    * This check will help us prevent cases like n+* or n*- et cetera*//*

                    *//*We remove the last operation and replace with a +.
                    * Probably the user had press n+ and then change his or her mind and now press -
                    * so we don't want to have something like n-+*//*
                    operationLists.removeLast() ;
                    operationLists.addLast(((Button) event.getSource()).getText());

                    System.out.println("I tried to add :D");

                    *//*We now print and update the operation area*//*
                    BasicFXUtilities.printOperations(operationLists,operationsArea);
                } else {
                    *//*Buffer not zero in this case will mean that
                    * we have something like n+ or n* or n- or n/
                    * So we remove the last operation, and replace it with + and then
                    * perform the add between what is in the operations list or current result and what is in the buffer*//*


                    *//*We remove the last operation and replace with a +.
                    * Probably the user had press n+ and then change his or her mind and now press -
                    * so we don't want to have something like n-+*//*
                    operationLists.removeLast() ;
                    operationLists.addLast(((Button) event.getSource()).getText());

                    if (!currentResult.isEmpty()){
                        *//*If current result is not empty then use it and add to the buffer*//*

                        operation = operationLists.getLast() ;

                        *//*We read what is in the buffer and use it as second number*//*
                        for (String item : buffer) {
                            secondNumber += item;
                        }

                        double result = BasicFXUtilities.performOperation(currentResult,secondNumber,operation) ;
                        answerArea.clear();
                        buffer.clear();
                        answerArea.appendText(String.valueOf(result));
                        lastBuffer = secondNumber ;
                        currentResult = String.valueOf(result);

                        operationLists.add(secondNumber);
                        operationLists.addLast(((Button) event.getSource()).getText()) ;

                        *//*We now print and update the operation area*//*
                        BasicFXUtilities.printOperations(operationLists,operationsArea);
                    } else {
                        *//*1: We now read all the numbers from the operations list up to and not including
                        * the operation and store as the first number
                        * 2: We read the operation and store in the operation's variable and then
                        * 3: read what is in the buffer and store as the second number
                        * 4: Perform the add operation*//*

                        *//*1 above*//*
                        for (String operationList : operationLists) {
                            System.out.println("Let us test: " + operationList);
                            if (!Objects.equals(operationList, ((Button) event.getSource()).getText())) {
                                firstNumber += operationList;
                            }
                        }

                        *//*2 above*//*
                        operation = operationLists.getLast() ;

                        *//*3 above*//*
                        for (String item : buffer) {
                            System.out.println("Let us test again: " + item);
                            secondNumber += item;
                        }

                        *//*We now add what is in the buffer to the operations list*//*
                        for (String item : buffer) {
                            operationLists.add(item);
                        }

                        *//*We now perform the add operation*//*
                        System.out.println("The first number is : " + firstNumber + " and second number is : " + secondNumber);
                        double result = BasicFXUtilities.performOperation(firstNumber,secondNumber,operation) ;
                        answerArea.clear(); //We clear the answer area
                        buffer.clear();
                        answerArea.appendText(String.valueOf(result)); //We now display the result
                        currentResult = String.valueOf(result); //We set the current result.
                        lastBuffer = secondNumber ; //We set the last buffer to be the second number

                        operationLists.addLast(((Button) event.getSource()).getText()) ;

                        *//*We now print and update the operation area*//*
                        BasicFXUtilities.printOperations(operationLists,operationsArea);
                    }
                }

            } else {
                *//*If operations list is not empty and the last element is not an operation, then it means that
                * We have something like n in the list or n + m
                * So we probably want to add a + to the operations list*//*

                *//*We first check if there are any previous results*//*
                *//*System.out.println("Current result: " + currentResult);
                System.out.println("Last buffer: " + lastBuffer); *//*


                System.out.println("What will I do here ehhh?");
            }
        } else {
            *//*We have done no operations before or we have clear all buffers and caches*//*
            if (buffer.size() == 0){
                *//*In case we have done no operations before, then this is probably the first
                time we launch the app and we press a +. Buffer is zero, it means we have not added any number yet
                So we should probably perform the add operation on the current result and the last buffer
                But we check if it exist first. This loop will be reached in case the user just press + on first start of the application*//*

                if (!currentResult.isEmpty()){
                    double result = BasicFXUtilities.performOperation(lastBuffer,currentResult,operation) ;
                    answerArea.clear(); //We clear the answer area
                    answerArea.appendText(String.valueOf(result)); //We now display the result
                    lastBuffer = currentResult ; //We set the last buffer to be the current result
                    currentResult = String.valueOf(result); //We set the current result.
                } else {
                    System.out.println("I think you should add some numbers first. :D");
                }
            } else {
                *//*If operations list is zero and buffer contains some items. This can either mean that
                * 1: The buffer is for the first number, so we have something like n in the buffer
                *
                * So we store the numbers in the buffer inside the operations list and on the operations area
                * and have something like this: n+ *//*

                for (int i = 0; i < buffer.size(); i++){
                    *//*Here we store all the elements in the buffer to the operations list*//*
                    operationLists.add(i,buffer.get(i));
                }

                *//*We now add a + to the end so that we now have something like n+*//*
                operationLists.addLast(((Button) event.getSource()).getText());

                *//*We now print and update the operation area*//*
                BasicFXUtilities.printOperations(operationLists,operationsArea);

                *//*We now clear the buffer so that we can use it for the second number*//*
                buffer.clear();
            }
        }*/
    }

    @FXML
    void performBackSpace(ActionEvent event) {

    }

    @FXML
    void performDivision(ActionEvent event) {
        displayResults(event);
    }

    @FXML
    void performMultiplication(ActionEvent event) {
        displayResults(event);
    }

    @FXML
    void performPercentage(ActionEvent event) {

    }

    @FXML
    void performReciprocal(ActionEvent event) {

    }

    @FXML
    void performSquareRoot(ActionEvent event) {
        String firstNumber = "" , secondNumber = "" ;
        /*We want to perform the squared root of a number
        * It can either be on a current result or on a number we just entered.*/

        if (BasicFXUtilities.getCurrentResult() != null){
            if (!BasicFXUtilities.getCurrentResult().isEmpty()) {
            /*Current result not empty means that we are either performing the operation with the current result
            * or with a buffer. In this case, we must check the operations list
            * We  could have something like this: n + sqrt(m)*/

                if (operationLists.size() > 0){
                /*We perform the last operation between the current result and the squared root of the buffer*/

                    if (buffer.size() > 0) {
                        operation = operationLists.getLast() ;

                        for (String item : buffer) {
                            secondNumber += item;
                        }

                        secondNumber = String.valueOf(Math.sqrt(Double.parseDouble(secondNumber)));

                        operation = "√" ;

                        double result = BasicFXUtilities.performOperation(BasicFXUtilities.getCurrentResult(), secondNumber, operation) ;

                        answerArea.clear();
                        buffer.clear();
                        answerArea.appendText(String.valueOf(result));

                        lastBuffer = secondNumber;
                        BasicFXUtilities.setCurrentResult(String.valueOf(result));
                        //currentResult = String.valueOf(result);

                        operationLists.add(((Button) event.getSource()).getText());
                        operationLists.add(secondNumber);

                        BasicFXUtilities.printOperations(operationLists,operationsArea);
                    } else {
                        System.out.println("check here =========");
                        if (!BasicFXUtilities.getCurrentResult().isEmpty()) {
                            double result = Math.sqrt(Double.parseDouble(BasicFXUtilities.getCurrentResult())) ;
                            answerArea.clear();
                            buffer.clear();
                            answerArea.appendText(String.valueOf(result));

                            operationLists.add(((Button) event.getSource()).getText());
                            operationLists.add(BasicFXUtilities.getCurrentResult());

                            System.out.println("The current result is now this: " + BasicFXUtilities.getCurrentResult());
                            System.out.println("The current result after computation is now this: " + result);

                            lastBuffer = BasicFXUtilities.getCurrentResult() ;
                            BasicFXUtilities.setCurrentResult(String.valueOf(result));
                            //currentResult = String.valueOf(result);

                            BasicFXUtilities.printOperations(operationLists,operationsArea);
                        } else {
                            System.out.printf("What do you think caused this? DEBUG");
                        }
                    }
                } else {
                /*Probably we have just the buffer. Can't think of a reason why, but we should just
                * do the sqrt of the current result and display*/

                    double result = Math.sqrt(Double.parseDouble(currentResult)) ;

                    answerArea.clear();
                    buffer.clear();
                    answerArea.appendText(String.valueOf(result));

                    operation = "√" ;

                    operationLists.add(((Button) event.getSource()).getText()) ;
                    operationLists.add(BasicFXUtilities.getCurrentResult());

                    lastBuffer = BasicFXUtilities.getCurrentResult() ;
                    BasicFXUtilities.setCurrentResult(String.valueOf(result));
                    //currentResult = String.valueOf(result) ;

                    BasicFXUtilities.printOperations(operationLists,operationsArea);
                }
            } else {
            /*Current result is empty. This may mean that this is the first time we are pressing the sqrt button
            * in this case*/

                System.out.println("Observe what will happen and decide");

                if (buffer.size() > 0) {
                /*Then perform squared root on the buffer*/

                    for (String item : buffer) {
                        firstNumber += item;
                    }

                    double result = Math.sqrt(Double.parseDouble(firstNumber)) ;
                    answerArea.clear();
                    buffer.clear();
                    answerArea.appendText(String.valueOf(result));

                    operation = "√" ;

                    lastBuffer = firstNumber;
                    BasicFXUtilities.setCurrentResult(String.valueOf(result));
                    //currentResult = String.valueOf(result) ;

                    operationLists.add(((Button) event.getSource()).getText()) ;
                    operationLists.add(firstNumber);

                    BasicFXUtilities.printOperations(operationLists,operationsArea);
                } else {
                /*Here we check if there is a current result and use*/
                    System.out.println("Maybe you should think of poping up to tell user to add some numbers");
                }
            }
        } else {
            System.out.println("Please DEBUG what may have happened here");
        }

    }

    @FXML
    void performSubtraction(ActionEvent event) {
        displayResults(event);
    }


    @FXML
    void printEight(ActionEvent event) {
        BasicFXUtilities.displayNumber(answerArea, buffer, event);
    }

    @FXML
    void printFive(ActionEvent event) {
        BasicFXUtilities.displayNumber(answerArea, buffer, event);
    }

    @FXML
    void printFour(ActionEvent event) {
        BasicFXUtilities.displayNumber(answerArea, buffer, event);
    }

    @FXML
    void printNine(ActionEvent event) {
        BasicFXUtilities.displayNumber(answerArea, buffer, event);
    }

    @FXML
    void printOne(ActionEvent event) {
        BasicFXUtilities.displayNumber(answerArea, buffer, event);
    }

    @FXML
    void printPoint(ActionEvent event) {
        if (BasicFXUtilities.getOperation() != null){
            operation = BasicFXUtilities.getOperation();
            BasicFXUtilities.displayPoint(answerArea,buffer,operation,event);
        }

    }

    @FXML
    void printSeven(ActionEvent event) {
        BasicFXUtilities.displayNumber(answerArea, buffer, event);
    }

    @FXML
    void printSix(ActionEvent event) {
        BasicFXUtilities.displayNumber(answerArea, buffer, event);
    }

    @FXML
    void printThree(ActionEvent event) {
        BasicFXUtilities.displayNumber(answerArea, buffer, event);
    }

    @FXML
    void printTwo(ActionEvent event) {
        BasicFXUtilities.displayNumber(answerArea, buffer, event);
    }

    @FXML
    void printZero(ActionEvent event) {
        BasicFXUtilities.displayZero(answerArea,buffer,operationLists,event);
    }

    @FXML
    void recalMemory(ActionEvent event) {

    }

    @FXML
    void removeFromMemory(ActionEvent event) {

    }

    @FXML
    void setMemory(ActionEvent event) {

    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert answerArea != null : "fx:id=\"answerArea\" was not injected: check your FXML file 'basic.fxml'.";
        assert operationsArea != null : "fx:id=\"operationsArea\" was not injected: check your FXML file 'basic.fxml'.";

    }

    /*Custom methods*/
    private void displayResults(ActionEvent event){
        if (BasicFXUtilities.getCurrentResult() != null) {
            currentResult = BasicFXUtilities.getCurrentResult();
            lastBuffer = BasicFXUtilities.getLastBuffer();
            operation = BasicFXUtilities.getOperation() ;

            BasicFXUtilities.performOperation(operationLists,buffer,operationsArea,answerArea,currentResult,operation,lastBuffer,event);
        } else {
            BasicFXUtilities.performOperation(operationLists,buffer,operationsArea,answerArea,currentResult,operation,lastBuffer,event);
        }
    }

    private void displayResultsFromEqualButton () {
        if (BasicFXUtilities.getCurrentResult() != null){
            currentResult = BasicFXUtilities.getCurrentResult();
            lastBuffer = BasicFXUtilities.getLastBuffer();
            operation = BasicFXUtilities.getOperation() ;

            BasicFXUtilities.performOperationUsingEqualButton(operationLists,buffer,answerArea,operationsArea, currentResult, operation, lastBuffer);
        } else {
            BasicFXUtilities.setCurrentResult("") ;
            BasicFXUtilities.performOperationUsingEqualButton(operationLists,buffer,answerArea,operationsArea, currentResult, operation, lastBuffer);
        }
    }
}
