[![Java CI with Maven][![Java CI with Maven](https://github.com/Lokankara/Souvenir/actions/workflows/maven.yml/badge.svg)](https://github.com/Lokankara/Souvenir/actions/workflows/maven.yml)

# Store

### Souvenirs

The file storage (a set of files of any structure) contains
information about souvenirs and their manufacturers.

#### Entities
For souvenirs, you need to store:
- name
- brand's details;
- date of issue;
- price.

  For manufacturers, you must store:
- name;
- country.

#### Service
- Implement the following features:
- Add, edit, view all manufacturers and all souvenirs.
- Display information about the souvenirs of a given brand.
- Display information about souvenirs made in a given country.
- Print information about manufacturers whose prices for souvenirs are less than the specified price.
- Print information about all manufacturers and, for each brand, print information
  about all the souvenirs it produces.
- Print information about the manufacturers of a given souvenir produced in a given year.
- For each year, print a list of souvenirs produced in that year.
- Delete a given brand and its souvenirs.

#### P.S. 
  We do not use databases (only files).
  We use collections to store data in the program. In the processing process
  use Streams (or do not use them if it is easier without them)
  Note. Different manufacturers may have souvenirs with the same name. For example,
  the souvenir "Branded Cup" may be available from the manufacturers "National University of
  Shipbuilding and Privatbank.

`mvn sonar:sonar`

`mvn clean install -X`
