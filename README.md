[![Java CI with Maven](https://github.com/Lokankara/Souvenir/actions/workflows/maven.yml/badge.svg)](https://github.com/Lokankara/Souvenir/actions/workflows/maven.yml)

# Store

## Souvenirs

The file storage (a set of files of any structure) contains
information about souvenirs and their brands.

#### Entities
For souvenirs, you need to store:
- name
- brand's details;
- date of issue;
- price.

For brands, you must store:
- name;
- country.

#### Service
- Implement the following features:
- Add, edit, view all brands and all souvenirs.
- Delete a given brand and its souvenirs.
- Display information about the souvenirs of a given brand.
- Display information about souvenirs made in a given country.
- View information about brands whose prices for souvenirs are less than the specified price.
- View information about all brands and, for each brand, print information
  about all the souvenirs it produces.
- View information about the brands of a given souvenir produced in a given year.
- For each year, print a list of souvenirs produced in that year.


#### P.S. 
  We do not use databases (only files).
  We use collections to store data in the program. In the processing process
  use Streams (or do not use them if it is easier without them)
  Note. Different brands may have souvenirs with the same name. For example,
  the souvenir "Branded Cup" may be available from the brands "National University of
  Shipbuilding and Privatbank.

`mvn clean install -X`

`java -jar ./target/souvenir-1.0-SNAPSHOT-jar-with-dependencies.jar`

http://localhost:8080/
http://localhost:8080/brand
http://localhost:8080/error
http://localhost:8080/static

# Holidays

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 18.1.1.

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The application will automatically reload if you change any of the source files.

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory.

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via a platform of your choice. To use this command, you need to first add a package that implements end-to-end testing capabilities.

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI Overview and Command Reference](https://angular.dev/tools/cli) page.

## Deployment

[deploying](https://country-holidays-angular.netlify.app/home)

[API](https://date.nager.at/swagger/index.html)


Here's a README template that includes essential instructions for building and starting your Angular application, as well as additional useful information:

---

# Angular Application

## Overview

This Angular application includes features for country search, displaying holidays, and navigating between different country-specific pages. It leverages lazy loading, routing, and HTTP services to provide a seamless user experience.

## Prerequisites

- **Node.js**: Ensure you have Node.js installed. You can download it from [nodejs.org](https://nodejs.org/).
- **Angular CLI**: Install Angular CLI globally if you haven't already:

  ```bash
  npm install -g @angular/cli
  ```

## Getting Started

### 1. Clone the Repository

Clone the repository to your local machine:

```bash
git clone <repository-url>
cd <repository-directory>
```

### 2. Install Dependencies

Navigate to the project directory and install the required dependencies:

```bash
npm install
```

### 3. Environment Configuration

If you have different environment configurations, ensure you have the appropriate `.env` or environment files. Typically, these are placed in the `src/environments` directory.

### 4. Build the Application

To build the application for production, run:

```bash
ng build --prod
```

This will generate the production-ready files in the `dist/` directory.

### 5. Start the Development Server

To run the application in development mode, use:

```bash
ng serve
```

By default, the application will be accessible at `http://localhost:4200`. You can change the port if needed by using:

```bash
ng serve --port <port-number>
```

### 6. Running End-to-End Tests

If end-to-end tests are configured, you can run them using:

```bash
ng e2e
```

### 7. Additional Commands

- **Linting**: To check for linting issues, run:

  ```bash
  ng lint
  ```

- **Formatting**: To format the codebase according to the configured Prettier or ESLint settings, run:

  ```bash
  ng format
  ```

## Application Structure

- **`src/app`**: Contains the main application modules, components, and services.
- **`src/environments`**: Configuration files for different environments (development, production, etc.).
- **`src/assets`**: Static assets such as images and stylesheets.

### Routing

- **`AppRoutingModule`**: Defines the main routing configuration.
- **`CountryRoutingModule`**: Handles routing for country-specific pages.

### Services

- **`CountryService`**: Provides methods to fetch country data and public holidays.
- **`HttpClientModule`**: Configured to make HTTP requests.

### Components

- **`HomeComponent`**: Displays the home page with a list of countries.
- **`CountryComponent`**: Shows detailed information about a previous country.
- **`CountryCardComponent`**: Shows detailed information about a specific country, including holidays.

## Contribution

Please follow the standard fork-and-pull request workflow. Ensure that your code adheres to the project's style guide and includes relevant tests.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
