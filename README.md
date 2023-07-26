[![Java CI with Maven](https://github.com/Lokankara/Souvenir/actions/workflows/maven.yml/badge.svg)](https://github.com/Lokankara/Souvenir/actions/workflows/maven.yml)

# Store

### Souvenirs

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
- Display information about the souvenirs of a given brand.
- Display information about souvenirs made in a given country.
- View information about brands whose prices for souvenirs are less than the specified price.
- View information about all brands and, for each brand, print information
  about all the souvenirs it produces.
- View information about the brands of a given souvenir produced in a given year.
- For each year, print a list of souvenirs produced in that year.
- Delete a given brand and its souvenirs.

#### P.S. 
  We do not use databases (only files).
  We use collections to store data in the program. In the processing process
  use Streams (or do not use them if it is easier without them)
  Note. Different brands may have souvenirs with the same name. For example,
  the souvenir "Branded Cup" may be available from the brands "National University of
  Shipbuilding and Privatbank.

`mvn sonar:sonar`

`mvn clean install -X`

`java -jar ./target/souvenir-1.0-SNAPSHOT.jar`

