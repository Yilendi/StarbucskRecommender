# StarbucskRecommender
android mobile app dev on starbucks recommendation

# One-line project summary 
- Creating an Android app that recommends a list of Starbucks drinks based on user preference in cup size, milk choice, and nutrition values.
# Descriptions of the project details.  
- Drinks class: represent the Starbucks drinks 
	- Variables: cup sizes, milk choices and nutrition levels
-  nutritionDB class: import, clean and interpret database from converted csv file (stored under res/raw)
- Using SQLite, or ArrayList, to import the nutrition data and wrangle the data by casting strings that represent numbers to integers and removing null data
- Find out the min, max, and the general distribution of the nutrition data cross all Starbucks drinks. This data is going to be helpful in asking the user to put in their ideal range of sugar level, caffeine level, etc.
- RecyclerAdapter class: responsible for RecyclerView implementation
	- Constructor of the RecyclerAdapter
	- Override the following methods: onCreateViewHolder, onBindViewHolder, getItemCount.
- MainActivity class (home page): initiate the app
	- Set up the nutrition database (nutritionDB)
	- Set up the RecyclerView
	- Welcome message from the developer (me)
	- Prompt the user to type in their name, and go to the next activity upon completion 
- RecyclerView class (data presentation page)
	- Show all the qualified drinks in RecyclerView
- BasicSettingsActivity class (second activity (page)): 
	- Ask the user to choose from a given set of cup sizes, milk choices, and usual sugar adjustment (go with standard amount of sugar, fewer pumps, or more pumps)
	- Filter Starbucks drinks into a new list called BasicList
- NutritionPreference classes (the rest of activities (pages))
	- Each activity (page) will provide the max and min of a particular nutrition data, and ask the user to select from a given range of nutrition values, such as caffeine and sugar level
	- Filter the drinks list based on user input and resulted drink list from the previous selection
	- Upon selection, prompt the user to the next nutrition range selection until all the nutrition range is defined, at which point the user will be able to see the final list in a RecyclerView

# Data sources
- extension://efaidnbmnnnibpcajpcglclefindmkaj/https://globalassets.starbucks.com/assets/867c147d5dcc46e9afb307093d5172e1.pdf




# Demo

https://user-images.githubusercontent.com/108242130/221301468-fc5dd10c-91c2-459f-959c-b982a84b27e1.mov

