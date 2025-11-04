--liquibase formatted sql

--changeset load_flavor:1
INSERT INTO flavor (
    id, extid, code, name, category, subcategory, description, notes,
    crunch, punch, sweet, savory, usage,
    created_at, updated_at, deleted_at, active
) VALUES
    -- Leafy Greens (crunch, punch, sweet, savory on 1-5 scale)
    (301, 'flv-romaine-001', 'FLVROM', 'Romaine Lettuce Flavor', 'Vegetable', 'Leafy Green',
     'Flavor profile for romaine lettuce', 'Crisp and mild',
     4, 1, 1, 1, 'Base', NOW(), NOW(), NULL, 1),

    (302, 'flv-spinach-001', 'FLVSPI', 'Spinach Flavor', 'Vegetable', 'Leafy Green',
     'Flavor profile for fresh spinach', 'Tender with slight earthiness',
     2, 1, 1, 2, 'Base', NOW(), NOW(), NULL, 1),

    (303, 'flv-kale-001', 'FLVKALE', 'Kale Flavor', 'Vegetable', 'Leafy Green',
     'Flavor profile for kale', 'Sturdy and slightly bitter',
     3, 2, 1, 2, 'Base', NOW(), NOW(), NULL, 1),

    (304, 'flv-arugula-001', 'FLVARG', 'Arugula Flavor', 'Vegetable', 'Leafy Green',
     'Flavor profile for arugula', 'Peppery and bold',
     2, 4, 1, 2, 'Base', NOW(), NOW(), NULL, 1),

    -- Vegetables
    (305, 'flv-tomato-001', 'FLVTOM', 'Tomato Flavor', 'Vegetable', 'Fruit Vegetable',
     'Flavor profile for tomatoes', 'Sweet and acidic',
     2, 2, 4, 3, 'Topping', NOW(), NOW(), NULL, 1),

    (306, 'flv-cucumber-001', 'FLVCUC', 'Cucumber Flavor', 'Vegetable', 'Fruit Vegetable',
     'Flavor profile for cucumber', 'Cool and refreshing',
     4, 1, 2, 1, 'Topping', NOW(), NOW(), NULL, 1),

    (307, 'flv-carrot-001', 'FLVCAR', 'Carrot Flavor', 'Vegetable', 'Root Vegetable',
     'Flavor profile for carrots', 'Sweet and earthy',
     5, 1, 4, 2, 'Topping', NOW(), NOW(), NULL, 1),

    (308, 'flv-bellpepper-001', 'FLVBEL', 'Bell Pepper Flavor', 'Vegetable', 'Fruit Vegetable',
     'Flavor profile for bell peppers', 'Sweet and slightly tangy',
     4, 2, 3, 2, 'Topping', NOW(), NOW(), NULL, 1),

    (309, 'flv-redonion-001', 'FLVRON', 'Red Onion Flavor', 'Vegetable', 'Bulb Vegetable',
     'Flavor profile for red onions', 'Sharp and pungent',
     3, 5, 2, 4, 'Topping', NOW(), NOW(), NULL, 1),

    -- Proteins
    (310, 'flv-chicken-001', 'FLVCHI', 'Grilled Chicken Flavor', 'Protein', 'Poultry',
     'Flavor profile for grilled chicken breast', 'Mild and savory',
     2, 2, 1, 5, 'Protein', NOW(), NOW(), NULL, 1),

    (311, 'flv-egg-001', 'FLVEGG', 'Hard Boiled Egg Flavor', 'Protein', 'Egg',
     'Flavor profile for hard boiled eggs', 'Rich and mild',
     1, 1, 1, 4, 'Protein', NOW(), NOW(), NULL, 1),

    (312, 'flv-chickpea-001', 'FLVCHP', 'Chickpea Flavor', 'Protein', 'Legume',
     'Flavor profile for cooked chickpeas', 'Nutty and earthy',
     2, 1, 2, 3, 'Protein', NOW(), NOW(), NULL, 1),

    (313, 'flv-feta-001', 'FLVFET', 'Feta Cheese Flavor', 'Protein', 'Dairy',
     'Flavor profile for feta cheese', 'Tangy and salty',
     1, 4, 1, 5, 'Protein', NOW(), NOW(), NULL, 1),

    -- Nuts & Seeds
    (314, 'flv-almond-001', 'FLVALM', 'Almond Flavor', 'Nuts', 'Tree Nut',
     'Flavor profile for almonds', 'Nutty with mild sweetness',
     5, 1, 2, 3, 'Topping', NOW(), NOW(), NULL, 1),

    (315, 'flv-walnut-001', 'FLVWAL', 'Walnut Flavor', 'Nuts', 'Tree Nut',
     'Flavor profile for walnuts', 'Earthy and slightly bitter',
     4, 2, 1, 3, 'Topping', NOW(), NOW(), NULL, 1),

    (316, 'flv-sunflower-001', 'FLVSUN', 'Sunflower Seeds Flavor', 'Seeds', 'Seed',
     'Flavor profile for sunflower seeds', 'Nutty and toasty',
     5, 1, 1, 3, 'Topping', NOW(), NOW(), NULL, 1),

    -- Dressings
    (317, 'flv-balsamic-001', 'FLVBAL', 'Balsamic Vinaigrette Flavor', 'Dressing', 'Vinaigrette',
     'Flavor profile for balsamic vinaigrette', 'Tangy with sweet notes',
     1, 3, 4, 3, 'Dressing', NOW(), NOW(), NULL, 1),

    (318, 'flv-ranch-001', 'FLVRAN', 'Ranch Dressing Flavor', 'Dressing', 'Creamy',
     'Flavor profile for ranch dressing', 'Creamy and herby',
     1, 2, 2, 5, 'Dressing', NOW(), NOW(), NULL, 1),

    (319, 'flv-caesar-001', 'FLVCAE', 'Caesar Dressing Flavor', 'Dressing', 'Creamy',
     'Flavor profile for caesar dressing', 'Garlicky and savory',
     1, 3, 1, 5, 'Dressing', NOW(), NOW(), NULL, 1),

    (320, 'flv-olive-001', 'FLVOLI', 'Olive Oil Flavor', 'Dressing', 'Oil',
     'Flavor profile for olive oil', 'Fruity and peppery',
     1, 2, 1, 4, 'Dressing', NOW(), NOW(), NULL, 1);
