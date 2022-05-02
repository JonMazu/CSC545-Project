
drop table coffees;

drop table mealPlan;
drop table containsRecipe;
drop table recipe;
drop table uses;
drop table ingredient;

--All primary keys should be set to autoincrement
create table mealPlan (
    planID char(3) primary key
);

create table recipe (
    recipeID char(5) primary key,
    name varchar2(20),
    category varchar2(20),
    instructions varchar2(32672)
    -- Not sure what best way to store instructions in DB is. Would depend on total length
    -- Instructions needs to be either varchar paragraph or a link based
);

create table ingredient (
    ingredientID char(6) primary key,
    name varchar2(20),
    category varchar2(20),
    calories number(3),
    sugar number(2),
    protein number(2),
    sodium number(2),
    fat number(2),
    inFridge boolean
    -- Are we keeping these as numbers or varchars? Do we want percentages to make it simple? Are we doing math with these?
    -- In planned example we created an amount variable rather than a boolean. Amount makes more sense that a boolean. 
    -- If amount == 0 then defacto we know there is none. If there is some, we likely want to know how many of that thing there is.
);

create table containsRecipe (
    planID char(3),
    recipeID char(5),
    day varchar2(10),
    meal varchar2(10)
    primary key(planID, recipeID),
    constraint planFK foreign key (planID) references mealPlan(planID) on delete cascade,
    constraint recipeFK foreign key (recipeID) references recipe(recipeID) on delete cascade
);

create table usesIngredient (
    recipeID char(5),
    ingredientID char(6),
    primary key(recipeID, ingredientID),
    constraint recipeFK foreign key (recipeID) references recipe(recipeID) on delete cascade,
    constraint ingredientFK foreign key (ingredientID) references ingredient(ingredientID) on delete cascade

    -- Is a uses table a redundancy? Are we able to reduce this to a smaller amount?
);

--Probably add some sample data here

commit;