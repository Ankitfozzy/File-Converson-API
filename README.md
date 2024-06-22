# File Conversion API

## Introduction

This project implements a Spring Boot application that provides an API to upload various types of files (Excel, Word, JSON, plain text) and convert them into PDF format. The application utilizes different libraries like Apache POI, iText, and Jackson to handle file processing and PDF generation.

## APIs

### POST API Signature and Payload

**Endpoint:** `POST /api/files/upload`

**Request:**

- **Content-Type:** `multipart/form-data`
- **Parameter:** `file` (the file to be uploaded and converted)

**Example using Postman:**

- Set method to `POST`.
- URL: `http://localhost:8080/api/files/upload`
- Body: Select `form-data`, add a key `file` and choose the file to upload.

### Response

- **Content-Type:** `application/pdf`
- The response will be a PDF file converted from the uploaded file.

## Implementation Details

The project consists of the following components:

### Controllers

- **FileUploadController:** Handles the file upload request and delegates the conversion task to the service.

### Services

- **FileProcessingService:** Implements the logic to detect the file type and convert it to a PDF using appropriate methods for each file type.

### Exception Handling

- **GlobalExceptionHandler:** Handles exceptions globally, ensuring consistent error responses.

### Entities and DTOs

- Not applicable in this context as we are dealing with file uploads and responses.

### Configuration

- **Libraries used:**
  - **Apache POI:** For handling Excel and Word documents.
  - **iText:** For PDF generation.
  - **Jackson:** For parsing JSON data.

### Dependencies

Add the following dependencies to your `pom.xml`:

```xml
<dependencies>
    <!-- Spring Boot dependencies -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- Apache POI for Excel and Word -->
    <dependency>
        <groupId>org.apache.poi</groupId>
        <artifactId>poi-ooxml</artifactId>
        <version>5.2.3</version>
    </dependency>
    <dependency>
        <groupId>org.apache.poi</groupId>
        <artifactId>poi-scratchpad</artifactId>
        <version>5.2.3</version>
    </dependency>

    <!-- iText for PDF -->
    <dependency>
        <groupId>com.itextpdf</groupId>
        <artifactId>itextpdf</artifactId>
        <version>5.5.13.2</version>
    </dependency>

    <!-- Jackson for JSON -->
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.15.2</version>
    </dependency>
</dependencies>
```

## Testing

### Using Postman

1. **Open Postman:**
   - If you don't have Postman installed, you can download it from [here](https://www.postman.com/downloads/).

2. **Create a new request:**
   - Click on "New" and select "HTTP Request".

3. **Set the request method to POST and enter the URL:**
   - Method: `POST`
   - URL: `http://localhost:8080/api/files/upload`

4. **Add the file to the request:**
   - Go to the "Body" tab.
   - Select "form-data".
   - In the "Key" field, enter `file`.
   - In the "Value" field, click on the "Select Files" button and choose a file from your system.

5. **Send the request:**
   - Click on the "Send" button.

6. **Check the response:**
   - The response should be a PDF file.

## Build and Run

### Build Commands

1. **Clone the repository:**

   ```bash
   git clone https://github.com/your-repository.git
   cd your-repository
   ```

2. **Build the project using Maven:**

   ```bash
   ./mvnw clean install
   ```

3. **Run the application:**

   ```bash
   java -jar target/your-application-name.jar
   ```

## Conclusion

The File Conversion API project demonstrates the implementation of a RESTful API using Spring Boot to handle file uploads and convert them to PDF. It provides a robust foundation for managing different file types and generating PDFs effectively.
