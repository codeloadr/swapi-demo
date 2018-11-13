# swapi-demo

## What is done
There are 2 branches that are in working condition:
- mvc
- mvvm

Both the branches have following functionality:
- Fetches all the Star Wars characters and their bio-data
- Search button on the toolbar enables free text search by characters name
- Show data in offline
- Allow search in the offline data

## Architectures
### MVC
The 'mvc' branch follows MVC architecture typically. It has most of the code within Activity and
allows cache through okhttp but no persistence. On people search, it will always make a network call
to fetch the characters by search text.

### MVVM
The 'mvvm' branch follows MVVM architecture. It has ViewModel that interacts with Room database
to store the network response to fetch all the star wars characters and returns observable list to
update the view on the list activity. On people search, it will always lookup the database any not
make any network call because at the start of the activity, a network call keeps updating
the database until it has all the characters.
Used the room database using architecture component and live observable list to keep updating the UI
as soon as the data changes in the database.

## Improvements
- Did not use the GraphQL.
- Did not use the Content provider.
- Did not add logging or fetch any images
- Cannot go to resources from details page