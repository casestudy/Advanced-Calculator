package utilities;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Created by Femencha Azombo Fabrice on 1/19/2017.
 */
public class BasicFXUtilities {

    private static String currentResult ;
    private static String lastBuffer ;
    private static String operation ;

    private static String memoryBuffer;

    private String uselessVariable ;

    /**
     * Displays a number between from 1 to 9 on the screen
     *
     * @param answerArea: The text area where it will add the decimal to
     * @param buffer: The buffer containing the other digits of the sequence
     * @param event: The event that detects what happened (In this case we pressed the point button)
     */
    public static void displayNumber (TextField answerArea, LinkedList<String> buffer, ActionEvent event){
        if (Objects.equals(answerArea.getText().trim(), "0")){
            answerArea.clear();
            answerArea.appendText(((Button) event.getSource()).getText()) ;
            buffer.add(((Button) event.getSource()).getText());
        } else if (buffer.size() == 0) {
            answerArea.clear();
            answerArea.appendText(((Button) event.getSource()).getText()) ;
            buffer.add(((Button) event.getSource()).getText());
        } else {
            answerArea.appendText(((Button) event.getSource()).getText()) ;
            buffer.add(((Button) event.getSource()).getText());
        }
    }

    /**
     * Adds a point (Decimal) in a number.
     * It makes sure a number cannot have more than two points
     *
     * @param answerArea: The text area where it will add the decimal to
     * @param buffer: The buffer containing the other digits of the sequence
     * @param event: The event that detects what happened (In this case we pressed the point button)
     */
    public static void displayPoint (TextField answerArea, LinkedList<String> buffer, String operation, ActionEvent event){
        System.out.printf("Fix this shit 1");
        try{
            if (!buffer.contains(".")) {
                System.out.printf("Fix this shit 2");
                if (buffer.size() == 0) {
                    System.out.printf("Fix this shit 3");
                    answerArea.clear();
                    answerArea.appendText("0");
                    answerArea.appendText(((Button) event.getSource()).getText());
                    buffer.add("0");
                    buffer.addLast(((Button) event.getSource()).getText());
                } else {
                    System.out.printf("Fix this shit 4");

                    if (!operation.isEmpty()){
                        System.out.printf("Fix this shit 5");
                        answerArea.clear();
                        buffer.clear();

                        answerArea.appendText("0");
                        answerArea.appendText(((Button) event.getSource()).getText());
                        buffer.add("0");
                        buffer.addLast(((Button) event.getSource()).getText());

                        System.out.println("I am here now");
                    } else {
                        System.out.printf("Fix this shit 6");
                        answerArea.appendText(((Button) event.getSource()).getText());
                        buffer.addLast(((Button) event.getSource()).getText());
                        System.out.println("Now I am here");
                    }
                }
            } else {
                System.out.printf("Fix this shit 7");
                System.out.printf("Fix this shit");
            }
        } catch (NoSuchElementException e){
            System.out.println("The buffer does not contain a point yet: " + e.getMessage());
        }
    }


    /**
     * Displays a zero, based on very select circumstances
     * @param answerArea: The text area where it will add the decimal to
     * @param buffer: The buffer containing the other digits of the sequence
     * @param operationLists: The list containing all our current operations
     * @param event: The event that detects what happened (In this case we pressed the point button)
     */
    public static void displayZero (TextField answerArea, LinkedList<String> buffer, LinkedList <String> operationLists, ActionEvent event) {
        if (buffer.size() == 0){

            /*
            There are no numbers in the buffer, so we check if the answer area already has a 0 and clear if so.
            So that we can avoid having this condition on the answer area: 0000000
            */
            if (!Objects.equals(answerArea.getText(), "0")) {
                answerArea.clear();
                answerArea.appendText(((Button) event.getSource()).getText());
                buffer.add(((Button) event.getSource()).getText()) ;
            }
        } else {
            /*
               Buffer is not zero, so there are some numbers in the buffer already.
               This means that we are appending the 0 to some number. We need to prevent the following
               scenarios from happening
               1: 2+0000000000 or 2*000000 or  et cetera
               2: 20000000
            */
            if (buffer.size() > 2){
                /*Checks if we have at least one (more than two) element in the buffer*/

                if (operationLists.size() > 0) {
                    /*we have some elements in the operations list. This means we tried adding an operator already. So we check if the but last element is
                     *an operation like + or - or * et cetera
                     */

                    if (Objects.equals(operationLists.get(operationLists.size() - 2), "+")
                            || Objects.equals(operationLists.get(operationLists.size() - 2), "-")
                            || Objects.equals(operationLists.get(operationLists.size() - 2), "*")
                            || Objects.equals(operationLists.get(operationLists.size() - 2), "÷")
                            || Objects.equals(operationLists.get(operationLists.size() - 2), "0")) {
                     /*If it is, then we don't add a zero, we do as below.
                      * An example can be: 2+0*/

                        answerArea.clear();
                        answerArea.appendText(((Button) event.getSource()).getText());
                    } else {
                        /*The but last element is not an operation, so we just append zero to the elements already there
                            * An example can be : 2000000*/

                        answerArea.appendText(((Button) event.getSource()).getText());
                        buffer.add(((Button) event.getSource()).getText());
                    }
                } else {
                    /*The operations list is empty. so we have not tried to press any operator
                    * But we check if we have a */

                    if (Objects.equals(buffer.get(buffer.size() - 1), ".")){
                        answerArea.appendText(((Button) event.getSource()).getText());
                        buffer.add(((Button) event.getSource()).getText());
                    } else if (Objects.equals(buffer.get(buffer.size() - 1), "0")){
                        System.out.println("I will not add a zero again");
                        /*if (Objects.equals(buffer.get(buffer.size() - 2), ".")){
                            System.out.println("I will not add a zero again");
                        } else {
                            answerArea.appendText(((Button) event.getSource()).getText());
                            buffer.add(((Button) event.getSource()).getText());
                        }*/
                    } else {
                        answerArea.appendText(((Button) event.getSource()).getText());
                        buffer.add(((Button) event.getSource()).getText());
                    }
                }
            } else {
                /*The buffer size is greater than zero and less than 2. So 0 is probably the first element we are pressing*/
                if (Objects.equals(answerArea.getText().trim(), "0")) {
                    answerArea.clear();
                    answerArea.appendText(((Button) event.getSource()).getText());
                } else{
                    /*What is found there is not a zero*/
                    answerArea.appendText(((Button) event.getSource()).getText());
                    buffer.add(((Button) event.getSource()).getText());
                }
            }
        }
    }


    /**
     * Displays all the operations we have all performed on the system.
     *
     * @param operationLists: The list containing all our current operations
     * @param operationsArea: The area where we will display all the operations we have currently carried out
     */
    public static void printOperations(LinkedList<String> operationLists, TextField operationsArea){

        System.out.println("The operations list is: " + operationLists);
        operationsArea.clear();

        System.out.println("The last position of the sqrt is: " + operationLists.lastIndexOf("√"));

        for (String operationList : operationLists) {
            if (Objects.equals(operationList, "+") || Objects.equals(operationList, "-") || Objects.equals(operationList, "*") || Objects.equals(operationList, "÷") || Objects.equals(operationList, "±")) {

                if (Objects.equals(operationList, "±")){
                    operationList = "-" ;
                }
                operationsArea.appendText(" " + operationList + " ");
            } else {
                operationsArea.appendText(operationList);
            }
        }
    }

    /**
     * @param operationLists: The list containing all our current operations
     * @return true if the last element in the operations list is an operation like +, -, *, / et cetera and false if not
     */
    private static boolean checkLastItemInOperationList(LinkedList<String> operationLists) {
        return Objects.equals(operationLists.getLast(), "+") || Objects.equals(operationLists.getLast(), "-") || Objects.equals(operationLists.getLast(), "*") || Objects.equals(operationLists.getLast(), "÷");
    }

    /**
     * This function performs an operation like +, -, *, / et cetera
     *
     * @param firstNumber: The first number that will be used in the operation
     * @param secondNumber: The second number that will be used in the operation
     * @param operation: The operation that will be performed
     */
    public static double performOperation(String firstNumber, String secondNumber, String operation) {
        switch (operation){
            case "+" :
                return Double.parseDouble(firstNumber) + Double.parseDouble(secondNumber) ;
            case "-" :
                return Double.parseDouble(firstNumber) - Double.parseDouble(secondNumber) ;
            case "*" :
                return Double.parseDouble(firstNumber) * Double.parseDouble(secondNumber) ;
            case "÷" :
                return Double.parseDouble(firstNumber) / Double.parseDouble(secondNumber) ;
            default:
                System.out.println("Something happened that I don't know. Fix it");

        }
        return 0 ;
    }

    /**
     * @param operationLists: The list containing all our current operations
     * @param buffer: The buffer containing the other digits of the sequence
     * @param operationsArea: The area where we will display all the operations we have currently carried out
     * @param answerArea: text area where it will add the decimal to
     * @param currentResult: The answer to the last operation done.
     * @param operation: The last operation done.
     * @param lastBuffer: The last 'second' number that was used in computation
     * @param event: The event that detects what happened (In this case we pressed the point button)
     */
    public static void performOperation(LinkedList <String> operationLists, LinkedList<String> buffer, TextField operationsArea, TextField answerArea, String currentResult, String operation, String lastBuffer, ActionEvent event) {
        String firstNumber = "", secondNumber = "" ;

        if (operationLists.size() > 0){
            /*If we have done some operations before. What ever the operations are. We have already have something like this
            * n+
            * It means we are pressing the operations for a second time. This might mean that we want
            * to add what is in the operations list (First number) and what we currently have in the buffer (Second number)*/

            if (BasicFXUtilities.checkLastItemInOperationList(operationLists)){
                /*If operations list is not empty, it means we either have something like
                * n in the list or n+ or n- et cetera in the list
                * So we check if the buffer is empty or not*/

                if (buffer.size() == 0){
                    /*Buffer is zero in this case will mean that, we have something like
                    * n+ or n- or n* or n/ et cetera and we try to press a + again.
                    * This check will help us prevent cases like n+* or n*- et cetera*/

                    /*We remove the last operation and replace with a +.
                    * Probably the user had press n+ and then change his or her mind and now press -
                    * so we don't want to have something like n-+*/
                    operationLists.removeLast() ;
                    operationLists.addLast(((Button) event.getSource()).getText());

                    /*We now print and update the operation area*/
                    BasicFXUtilities.printOperations(operationLists,operationsArea);
                } else {
                    /*Buffer not zero in this case will mean that
                    * we have something like n+ or n* or n- or n/
                    * So we remove the last operation, and replace it with + and then
                    * perform the add between what is in the operations list or current result and what is in the buffer*/

                    if (!currentResult.isEmpty()){
                        /*If current result is not empty then use it and add to the buffer*/

                        operation = operationLists.getLast() ;

                        setOperation(operation);

                        /*We read what is in the buffer and use it as second number*/
                        for (String item : buffer) {
                            if (Objects.equals(item, "±"))
                                item = "-" ;
                            secondNumber += item;
                        }

                        System.out.println("The first number is now this: " + currentResult + " and the second number is: " + secondNumber);
                        double result = BasicFXUtilities.performOperation(currentResult,secondNumber,operation) ;
                        answerArea.clear();
                        buffer.clear();
                        answerArea.appendText(String.valueOf(result));
                        lastBuffer = secondNumber ;
                        currentResult = String.valueOf(result);

                        setCurrentResult(currentResult);
                        setLastBuffer(lastBuffer);

                        System.out.println("Check here ==============");


                        operationLists.add(secondNumber);
                        operationLists.addLast(((Button) event.getSource()).getText()) ;

                        /*We now print and update the operation area*/
                        BasicFXUtilities.printOperations(operationLists,operationsArea);
                    } else {
                        /*1: We now read all the numbers from the operations list up to and not including
                        * the operation and store as the first number
                        * 2: We read the operation and store in the operation's variable and then
                        * 3: read what is in the buffer and store as the second number
                        * 4: Perform the add operation*/

                        /*1 above*/
                        for (String operationList : operationLists) {
                            if (!Objects.equals(operationList, "+") && !Objects.equals(operationList, "-") && !Objects.equals(operationList, "*") && !Objects.equals(operationList, "÷")) {
                                firstNumber += operationList;
                            }
                        }

                        /*2 above*/
                        operation = operationLists.getLast() ;

                        setOperation(operation);

                        /*3 above*/
                        for (String item : buffer) {
                            if (Objects.equals(item, "±"))
                                item = "-" ;
                            secondNumber += item;
                        }

                        /*We now add what is in the buffer to the operations list*/
                        for (String item : buffer) {
                            operationLists.add(item);
                        }

                        /*We now perform the operation*/
                        if (Objects.equals(firstNumber.substring(0, 1), "±")){
                            firstNumber = firstNumber.replace("±" , "-") ;
                        }

                        double result = BasicFXUtilities.performOperation(firstNumber,secondNumber,operation) ;
                        answerArea.clear(); //We clear the answer area
                        buffer.clear();
                        answerArea.appendText(String.valueOf(result)); //We now display the result

                        currentResult = String.valueOf(result); //We set the current result.
                        lastBuffer = secondNumber ; //We set the last buffer to be the second number

                        setCurrentResult(currentResult);
                        setLastBuffer(lastBuffer);

                        operationLists.addLast(((Button) event.getSource()).getText()) ;

                        /*We now print and update the operation area*/
                        BasicFXUtilities.printOperations(operationLists,operationsArea);
                    }
                }
            } else {
                /*If operations list is not empty and the last element is not an operation, then it means that
                * We have something like n in the list or n + m
                * So we probably want to add a + to the operations list*/

                /*We first check if there are any previous results*/
                /*System.out.println("Current result: " + currentResult);
                System.out.println("Last buffer: " + lastBuffer); */

                System.out.println("What will I do here ehhh?, what really happened?");

                operationLists.addLast(((Button) event.getSource()).getText());

                    /*We now print and update the operation area*/
                BasicFXUtilities.printOperations(operationLists,operationsArea);
            }
        } else {
            /*We have done no operations before or we have clear all buffers and caches*/
            if (buffer.size() == 0){
                /*In case we have done no operations before, then this is probably the first
                time we launch the app and we press a +. Buffer is zero, it means we have not added any number yet
                So we should probably perform the add operation on the current result and the last buffer
                But we check if it exist first. This loop will be reached in case the user just press + on first start of the application*/

                if (!currentResult.isEmpty()){
                    /*Now we check if the operations area is empty. That is we are pressing an operation
                    * After we have done = or something else*/

                    if (operationsArea.getText().trim().isEmpty()){
                        /*It means we are from equal to, so here we should add the current result to the operations list and add the operation.
                        * The user wil surely add a number in the buffer so as to perform an operation on the
                        * current result and what will be in buffer*/

                        operationLists.add(currentResult);
                        operationLists.addLast(((Button) event.getSource()).getText());

                        printOperations(operationLists,operationsArea);
                    } else {
                        /*It means tha we are not from equal to. SO we just ahead to perform what was in
                        * last buffer and current result*/
                        double result = BasicFXUtilities.performOperation(lastBuffer,currentResult,operation) ;
                        answerArea.clear(); //We clear the answer area
                        answerArea.appendText(String.valueOf(result)); //We now display the result
                        lastBuffer = currentResult ; //We set the last buffer to be the current result
                        currentResult = String.valueOf(result); //We set the current result.

                        setCurrentResult(currentResult);
                        setLastBuffer(lastBuffer);
                    }
                } else {
                    System.out.println("I think you should add some numbers first. :D");
                }
            } else {
                /*If operations list is zero and buffer contains some items. This can either mean that
                * 1: The buffer is for the first number, so we have something like n in the buffer
                *
                * So we store the numbers in the buffer inside the operations list and on the operations area
                * and have something like this: n+ */

                for (int i = 0; i < buffer.size(); i++){
                    /*Here we store all the elements in the buffer to the operations list*/
                    operationLists.add(i,buffer.get(i));
                }

                /*We now add a + to the end so that we now have something like n+*/
                operationLists.addLast(((Button) event.getSource()).getText());

                /*We now print and update the operation area*/
                BasicFXUtilities.printOperations(operationLists,operationsArea);

                /*We now clear the buffer so that we can use it for the second number*/
                buffer.clear();
            }
        }
    }

    /**
     * This function performs an operation using the equal (=) button
     *
     * @param operationLists: The list containing all our current operations
     * @param buffer: The buffer containing the other digits of the sequence
     * @param operationsArea: The area where we will display all the operations we have currently carried out
     * @param answerArea: text area where it will add the decimal to
     * @param currentResult: The answer to the last operation done.
     * @param operation: The last operation done.
     * @param lastBuffer: The last 'second' number that was used in computation
     */
    @SuppressWarnings("Duplicates")
    public static void performOperationUsingEqualButton(LinkedList <String> operationLists, LinkedList <String> buffer, TextField answerArea, TextField operationsArea, String currentResult, String operation, String lastBuffer) {
        String firstNumber = "", secondNumber = "" ;

        /*We want to calculate using the = button. So we first check
        * If we are pressing it for the first time, that is we have no current results*/

        if (BasicFXUtilities.getCurrentResult().isEmpty() || BasicFXUtilities.getCurrentResult() == null) {
            /*If current result is empty, then it means that we are just pressing the = button for the first time
            * Either by first launch or we have entered some numbers in the
            * buffers and operations list*/

            if (operationLists.size() > 0) {
                /*This means that we have entered some entered some numbers and perhaps and operation
                * We may have something like this: n or n+ or n- et cetera and then press =
                * So we must first check which case it is*/

                if (Objects.equals(operationLists.getLast(), "+") || Objects.equals(operationLists.getLast(), "-") || Objects.equals(operationLists.getLast(), "*") || Objects.equals(operationLists.getLast(), "÷")) {
                    /*This will mean that we have a case like this: n+ or n- et cetera
                    * so we have a first number and an operation
                    * we now check if we have a second number. This should be found in the buffer*/

                    if (buffer.size() > 0) {
                        /*This means we have a second number, so we can operate*/

                        for (int i = 0; i < operationLists.size(); i++) {
                            String operationList = operationLists.get(i);

                            if (!Objects.equals(operationList, "+") && !Objects.equals(operationList, "-") && !Objects.equals(operationList, "*") && !Objects.equals(operationList, "÷")) {

                                if (Objects.equals(operationLists.get(i), "√")){
                                    firstNumber = operationLists.get(operationLists.size() - 1) ;
                                } else {
                                    firstNumber += operationList;
                                }
                            }
                        }

                        operation = operationLists.getLast() ;
                        setOperation(operation);
                        BasicFXUtilities.setOperation(operation);

                        for (String item : buffer) {
                            secondNumber += item ;
                        }

                        /*We now perform the operation*/
                        if (Objects.equals(firstNumber.substring(0, 1), "±")){
                            firstNumber = firstNumber.replace("±" , "-") ;
                        }

                        if (Objects.equals(secondNumber.substring(0, 1), "±")){
                            secondNumber = secondNumber.replace("±" , "-") ;
                        }

                        System.out.println("The first number iis: " + firstNumber + " and the second number is: " + secondNumber);
                        double result = BasicFXUtilities.performOperation(firstNumber,secondNumber,operation);
                        answerArea.clear();
                        operationLists.clear();
                        buffer.clear();
                        operationsArea.clear();

                        answerArea.appendText(String.valueOf(result));
                        currentResult = String.valueOf(result);
                        lastBuffer = secondNumber ;

                        setCurrentResult(currentResult);
                        setLastBuffer(lastBuffer);
                    } else {
                        /*This means we have no second number, so we cannot operate
                        * May be we alert here and ask for a second number*/
                        System.out.println("Perhaps we will do an alert asking the user to input a number");
                    }

                } else {
                    /*This means that we have a case like this: n with no operation
                    * That is there are some operations but there is not a last operation
                    * Probably we did a square root operation*/
                    System.out.println("We will decide whether to continue or not");
                }
            }
        } else {
            /*The current result is not empty, this means that the last buffer is probably also not empty.
            * So the user either wants to perform the last operation by pressing an equal to again *
            * or has enter a number (buffer) that we will use to compute
            * Quite unsure of the later*/

            if (operationLists.size() == 0) {
                /*This means that we are pressing the = sign for a second consecutive time
                * SO we add the last buffer and current result. The difference here is, current buffer
                * will remain the last value it was and will not be updated*/

                double result = performOperation(currentResult,lastBuffer,operation) ;
                answerArea.clear();
                operationsArea.clear();
                buffer.clear();
                operationLists.clear();

                answerArea.appendText(String.valueOf(result));
                currentResult = String.valueOf(result) ;
                setCurrentResult(currentResult);
            } else {
                /*This means that we are pressing equal to after we have done some operations
                * and we certainly have a buffer so we will check if we have a buffer here*/

                if (buffer.size() == 0) {
                    System.out.println("The last buffer is: " + getLastBuffer());
                    System.out.println("The current result is: " + getCurrentResult());

                    /*Set last buffer to current result*/
                    /*if (Double.parseDouble(getCurrentResult()) == 0) {
                        System.out.println("I will not change the last buffer just use it");
                    } else
                        setLastBuffer(getCurrentResult());*/


                    if (operationLists.size() > 0) {
                        String temp = operationLists.getLast();
                        double result = performOperation(getCurrentResult(), getLastBuffer(), operationLists.getLast()) ;

                        answerArea.clear();
                        operationsArea.clear();
                        buffer.clear();
                        operationLists.clear();

                        operationLists.add(temp) ;

                        System.out.println("The operation is this again: " + operationLists.getLast());

                        answerArea.appendText(String.valueOf(result));
                        currentResult = String.valueOf(result) ;
                        setCurrentResult(currentResult);
                    } else {
                        System.out.println("I am unsure of what is happening here.");
                    }
                } else {
                    /*Buffer is a not zero, so we will use the current result and the buffer to perform the operation
                    * I am unsure why tho. Keep watch for possible errors*/

                    operation = operationLists.getLast() ;
                    setOperation(operation);

                    BasicFXUtilities.setOperation(operation);

                    for (String item : buffer) {
                        secondNumber += item ;
                    }

                    double result = BasicFXUtilities.performOperation(currentResult,secondNumber,operation);
                    answerArea.clear();
                    operationLists.clear();
                    buffer.clear();
                    operationsArea.clear();

                    answerArea.appendText(String.valueOf(result));
                    currentResult = String.valueOf(result);
                    lastBuffer = secondNumber ;
                    setCurrentResult(currentResult);
                    setLastBuffer(lastBuffer);
                }
            }
        }
    }

    public static String getCurrentResult() {
        return currentResult;
    }

    public static void setCurrentResult(String cr) {
        currentResult = cr;
    }

    public static String getLastBuffer() {
        return lastBuffer;
    }

    public static void setLastBuffer(String lb) {
        lastBuffer = lb;
    }

    public static String getOperation() {
        return operation;
    }

    public static void setOperation(String op) {
        operation = op ;
    }


    public static String getMemoryBuffer() {
        return memoryBuffer;
    }

    public static void setMemoryBuffer(String mb) {
        memoryBuffer = mb;
    }

    public static void informationAlerts(String title, String type, String headerText, String message){

        Alert alert ;

        switch (type) {
            case "ERROR":
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(title);
                alert.setHeaderText(headerText);
                alert.setContentText(message);
                alert.showAndWait() ;
                break;

            case "WARNING":
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(title);
                alert.setHeaderText(headerText);
                alert.setContentText(message);
                alert.showAndWait() ;
                break;

            case "CONFIRMATION":
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle(title);
                alert.setHeaderText(headerText);
                alert.setContentText(message);
                alert.showAndWait() ;
                break;

        }
    }

    public String getUselessVariable() {
        return uselessVariable;
    }

    public void setUselessVariable(String uselessVariable) {
        this.uselessVariable = uselessVariable;
    }
}
