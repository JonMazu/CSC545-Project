
drop table mealPlan;
drop table containsRecipe cascade constraints;
drop table recipe;
drop table usesIngredient cascade constraints;
drop table ingredient;

--All primary keys should be set to autoincrement
create table mealPlan (
    planID char(3) primary key,
    planName varchar2(20)
);

create table recipe (
    recipeID char(5) primary key,
    name varchar2(20),
    category varchar2(20),
    instructions varchar2(32672)
);

create table ingredient (
    ingredientID char(6) primary key,
    name varchar2(20),
    foodGroup varchar2(20),
    calories number(3),
    sugar number(2),
    protein number(2),
    sodium number(2),
    fat number(2),
    inFridge boolean
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
);


insert into mealPlan values ('111', 'Week 1');

insert into ingredient values ('111111', 'flour', 'grain', 150, 5, 2, 1, 3, false);
insert into ingredient values ('111112', 'cheese', 'dairy', 300, 1, 4, 3, 6, false);
insert into ingredient values ('111113', 'tomato sauce', 'vegetable', 100, 2, 1, 3, 3, true);
insert into ingredient values ('111114', 'olive oil', 'vegetable', 125, 0, 0, 2, 5, true);
insert into ingredient values ('111115', 'meatballs', 'protein', 200, 0, 6, 4, 3, false);
insert into ingredient values ('111116', 'spaghetti noodles', 'grain', 150, 0, 1, 1, 2, true);
insert into ingredient values ('111117', 'egg', 'protein', 300, 0, 7, 1, 2, true);
insert into ingredient values ('111118', 'butter', 'dairy', 150, 0, 1, 3, 4, false);

insert into recipe values ('11111', 'pizza', 'italian', '1. Mix flour with water and oil. 2. Spread sauce on dough. 3. Put shredded cheese on sauce. 4. Bake');
insert into recipe values ('11112', 'spaghetti', 'italian', '1. Boil spaghetti noodles in water. 2. Put sauce on noodles. 3. Put meatballs on sauce. 4. Add cheese to taste');
insert into recipe values ('11113', 'scrambled eggs', 'american', '1. Crack eggs in bowl. 2. Use fork to scramble eggs. 3. Melt butter in pan. 4. Cook eggs in pan while stirring');

//Pizza
insert into usesIngredient values ('11111', '111111');
insert into usesIngredient values ('11111', '111112');
insert into usesIngredient values ('11111', '111113');
insert into usesIngredient values ('11111', '111114');

//Spaghetti
insert into usesIngredient values ('11112', '111116');
insert into usesIngredient values ('11112', '111113');
insert into usesIngredient values ('11112', '111115');
insert into usesIngredient values ('11112', '111112');

//Scrambled eggs
insert into usesIngredient values ('11113', '111117');
insert into usesIngredient values ('11113', '111118');

insert into containsRecipe values ('111', '11111', 'Monday', 'Lunch');
insert into containsRecipe values ('111', '11112', 'Monday', 'Dinner');
insert into containsRecipe values ('111', '11113', 'Monday', 'Breakfast');

commit;
