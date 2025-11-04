--liquibase formatted sql

--changeset load_nutrition:1
INSERT INTO nutrition (
    id, extid, code, name, category, subcategory, description, notes,
    carbohydrate, fat, protein, sugar,
    created_at, updated_at, deleted_at, active
) VALUES
    -- Leafy Greens (per 100g)
    (201, 'nut-romaine-001', 'NUTROM', 'Romaine Lettuce Nutrition', 'Vegetable', 'Leafy Green',
     'Nutritional profile for romaine lettuce', 'Low calorie, high in vitamins A and K',
     3, 0, 1, 1, NOW(), NOW(), NULL, 1),

    (202, 'nut-spinach-001', 'NUTSPI', 'Spinach Nutrition', 'Vegetable', 'Leafy Green',
     'Nutritional profile for fresh spinach', 'Rich in iron and vitamins',
     4, 0, 3, 0, NOW(), NOW(), NULL, 1),

    (203, 'nut-kale-001', 'NUTKALE', 'Kale Nutrition', 'Vegetable', 'Leafy Green',
     'Nutritional profile for kale', 'Superfood with high nutrient density',
     9, 1, 4, 2, NOW(), NOW(), NULL, 1),

    (204, 'nut-arugula-001', 'NUTARG', 'Arugula Nutrition', 'Vegetable', 'Leafy Green',
     'Nutritional profile for arugula', 'Peppery greens with antioxidants',
     4, 1, 3, 2, NOW(), NOW(), NULL, 1),

    -- Vegetables
    (205, 'nut-tomato-001', 'NUTTOM', 'Tomato Nutrition', 'Vegetable', 'Fruit Vegetable',
     'Nutritional profile for tomatoes', 'Rich in lycopene and vitamin C',
     4, 0, 1, 3, NOW(), NOW(), NULL, 1),

    (206, 'nut-cucumber-001', 'NUTCUC', 'Cucumber Nutrition', 'Vegetable', 'Fruit Vegetable',
     'Nutritional profile for cucumber', 'Very hydrating, low calorie',
     4, 0, 1, 2, NOW(), NOW(), NULL, 1),

    (207, 'nut-carrot-001', 'NUTCAR', 'Carrot Nutrition', 'Vegetable', 'Root Vegetable',
     'Nutritional profile for carrots', 'High in beta-carotene and fiber',
     10, 0, 1, 5, NOW(), NOW(), NULL, 1),

    (208, 'nut-bellpepper-001', 'NUTBEL', 'Bell Pepper Nutrition', 'Vegetable', 'Fruit Vegetable',
     'Nutritional profile for bell peppers', 'Excellent source of vitamin C',
     6, 0, 1, 4, NOW(), NOW(), NULL, 1),

    (209, 'nut-redonion-001', 'NUTRON', 'Red Onion Nutrition', 'Vegetable', 'Bulb Vegetable',
     'Nutritional profile for red onions', 'Contains quercetin antioxidant',
     9, 0, 1, 4, NOW(), NOW(), NULL, 1),

    -- Proteins
    (210, 'nut-chicken-001', 'NUTCHI', 'Grilled Chicken Nutrition', 'Protein', 'Poultry',
     'Nutritional profile for grilled chicken breast', 'Lean protein source',
     0, 4, 31, 0, NOW(), NOW(), NULL, 1),

    (211, 'nut-egg-001', 'NUTEGG', 'Hard Boiled Egg Nutrition', 'Protein', 'Egg',
     'Nutritional profile for hard boiled eggs', 'Complete protein with healthy fats',
     1, 11, 13, 1, NOW(), NOW(), NULL, 1),

    (212, 'nut-chickpea-001', 'NUTCHP', 'Chickpea Nutrition', 'Protein', 'Legume',
     'Nutritional profile for cooked chickpeas', 'Plant protein with fiber',
     27, 3, 9, 5, NOW(), NOW(), NULL, 1),

    (213, 'nut-feta-001', 'NUTFET', 'Feta Cheese Nutrition', 'Protein', 'Dairy',
     'Nutritional profile for feta cheese', 'Tangy cheese with calcium',
     4, 21, 14, 4, NOW(), NOW(), NULL, 1),

    -- Nuts & Seeds
    (214, 'nut-almond-001', 'NUTALM', 'Almond Nutrition', 'Nuts', 'Tree Nut',
     'Nutritional profile for almonds', 'Healthy fats and protein',
     22, 49, 21, 4, NOW(), NOW(), NULL, 1),

    (215, 'nut-walnut-001', 'NUTWAL', 'Walnut Nutrition', 'Nuts', 'Tree Nut',
     'Nutritional profile for walnuts', 'Omega-3 fatty acids',
     14, 65, 15, 3, NOW(), NOW(), NULL, 1),

    (216, 'nut-sunflower-001', 'NUTSUN', 'Sunflower Seeds Nutrition', 'Seeds', 'Seed',
     'Nutritional profile for sunflower seeds', 'Vitamin E and selenium',
     20, 51, 21, 3, NOW(), NOW(), NULL, 1),

    -- Dressings (per 100ml)
    (217, 'nut-balsamic-001', 'NUTBAL', 'Balsamic Vinaigrette Nutrition', 'Dressing', 'Vinaigrette',
     'Nutritional profile for balsamic vinaigrette', 'Light dressing option',
     15, 25, 0, 12, NOW(), NOW(), NULL, 1),

    (218, 'nut-ranch-001', 'NUTRAN', 'Ranch Dressing Nutrition', 'Dressing', 'Creamy',
     'Nutritional profile for ranch dressing', 'Creamy dressing',
     8, 52, 1, 4, NOW(), NOW(), NULL, 1),

    (219, 'nut-caesar-001', 'NUTCAE', 'Caesar Dressing Nutrition', 'Dressing', 'Creamy',
     'Nutritional profile for caesar dressing', 'Classic creamy dressing',
     7, 45, 2, 3, NOW(), NOW(), NULL, 1),

    (220, 'nut-olive-001', 'NUTOLI', 'Olive Oil Nutrition', 'Dressing', 'Oil',
     'Nutritional profile for olive oil', 'Pure healthy fat',
     0, 100, 0, 0, NOW(), NOW(), NULL, 1);
