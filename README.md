# Scrabble Words Helper

## Overview
This app allows users check if a word is considered a valid word (based on the words this app has saved in the database) and also check how much points a word is worth in the Scrabble game. It also allows users to add new words to the database. 

## Running the application

### Backend
1. navigate to the backend directory
    ```bash
    cd scrabble-backend
    ```
2. **Install dependencies:**
   ```bash
   ./gradlew clean build
    ```
3. Run the application
    ```bash
    ./gradlew bootRun
    ```
4. To use different words list to initialize database, change the path in the property 'file.words.path', or change the words list in the dictionary.txt file saved in the src-> main -> resources
 
### Frontend
1. navigate to the frontend directory
    ```bash
    cd scrabble-frontend
    ```
2. **Install dependencies:**
    ```bash
    npm install
     ```
3. run the application
    ```bash
    npm start
    ```
4. Application should be accessible at http://localhost:4200/

## Version Details
- Java 23
- Angular 19
