--liquibase formatted sql

--changeset load_serving:1
INSERT INTO serving (
    id, extid, code, name, category, subcategory, description, notes,
    cup, quarter, tablespoon, teaspoon, gram,
    created_at, updated_at, deleted_at, active
) VALUES
    -- Leafy Greens (loosely packed)
    (401, 'srv-romaine-001', 'SRVROM', 'Romaine Lettuce Serving', 'Vegetable', 'Leafy Green',
     'Standard serving for romaine lettuce', 'Chopped or shredded',
     47, 12, 3, 1, 100, NOW(), NOW(), NULL, 1),

    (402, 'srv-spinach-001', 'SRVSPI', 'Spinach Serving', 'Vegetable', 'Leafy Green',
     'Standard serving for fresh spinach', 'Loosely packed',
     30, 8, 2, 1, 100, NOW(), NOW(), NULL, 1),

    (403, 'srv-kale-001', 'SRVKALE', 'Kale Serving', 'Vegetable', 'Leafy Green',
     'Standard serving for kale', 'Chopped leaves',
     67, 17, 4, 1, 100, NOW(), NOW(), NULL, 1),

    (404, 'srv-arugula-001', 'SRVARG', 'Arugula Serving', 'Vegetable', 'Leafy Green',
     'Standard serving for arugula', 'Loosely packed',
     20, 5, 1, 0, 100, NOW(), NOW(), NULL, 1),

    -- Vegetables (chopped)
    (405, 'srv-tomato-001', 'SRVTOM', 'Tomato Serving', 'Vegetable', 'Fruit Vegetable',
     'Standard serving for tomatoes', 'Diced or sliced',
     149, 37, 9, 3, 100, NOW(), NOW(), NULL, 1),

    (406, 'srv-cucumber-001', 'SRVCUC', 'Cucumber Serving', 'Vegetable', 'Fruit Vegetable',
     'Standard serving for cucumber', 'Sliced with peel',
     104, 26, 7, 2, 100, NOW(), NOW(), NULL, 1),

    (407, 'srv-carrot-001', 'SRVCAR', 'Carrot Serving', 'Vegetable', 'Root Vegetable',
     'Standard serving for carrots', 'Shredded or julienned',
     110, 28, 7, 2, 100, NOW(), NOW(), NULL, 1),

    (408, 'srv-bellpepper-001', 'SRVBEL', 'Bell Pepper Serving', 'Vegetable', 'Fruit Vegetable',
     'Standard serving for bell peppers', 'Diced or sliced',
     92, 23, 6, 2, 100, NOW(), NOW(), NULL, 1),

    (409, 'srv-redonion-001', 'SRVRON', 'Red Onion Serving', 'Vegetable', 'Bulb Vegetable',
     'Standard serving for red onions', 'Sliced or diced',
     160, 40, 10, 3, 100, NOW(), NOW(), NULL, 1),

    -- Proteins
    (410, 'srv-chicken-001', 'SRVCHI', 'Grilled Chicken Serving', 'Protein', 'Poultry',
     'Standard serving for grilled chicken breast', 'Sliced or diced',
     140, 35, 9, 3, 100, NOW(), NOW(), NULL, 1),

    (411, 'srv-egg-001', 'SRVEGG', 'Hard Boiled Egg Serving', 'Protein', 'Egg',
     'Standard serving for hard boiled eggs', 'Sliced or chopped',
     136, 34, 9, 3, 100, NOW(), NOW(), NULL, 1),

    (412, 'srv-chickpea-001', 'SRVCHP', 'Chickpea Serving', 'Protein', 'Legume',
     'Standard serving for cooked chickpeas', 'Whole or mashed',
     164, 41, 10, 3, 100, NOW(), NOW(), NULL, 1),

    (413, 'srv-feta-001', 'SRVFET', 'Feta Cheese Serving', 'Protein', 'Dairy',
     'Standard serving for feta cheese', 'Crumbled',
     150, 38, 9, 3, 100, NOW(), NOW(), NULL, 1),

    -- Nuts & Seeds
    (414, 'srv-almond-001', 'SRVALM', 'Almond Serving', 'Nuts', 'Tree Nut',
     'Standard serving for almonds', 'Sliced or whole',
     143, 36, 9, 3, 100, NOW(), NOW(), NULL, 1),

    (415, 'srv-walnut-001', 'SRVWAL', 'Walnut Serving', 'Nuts', 'Tree Nut',
     'Standard serving for walnuts', 'Chopped or halves',
     120, 30, 8, 3, 100, NOW(), NOW(), NULL, 1),

    (416, 'srv-sunflower-001', 'SRVSUN', 'Sunflower Seeds Serving', 'Seeds', 'Seed',
     'Standard serving for sunflower seeds', 'Hulled seeds',
     140, 35, 9, 3, 100, NOW(), NOW(), NULL, 1),

    -- Dressings (liquid)
    (417, 'srv-balsamic-001', 'SRVBAL', 'Balsamic Vinaigrette Serving', 'Dressing', 'Vinaigrette',
     'Standard serving for balsamic vinaigrette', 'Typical portion',
     236, 59, 15, 5, 100, NOW(), NOW(), NULL, 1),

    (418, 'srv-ranch-001', 'SRVRAN', 'Ranch Dressing Serving', 'Dressing', 'Creamy',
     'Standard serving for ranch dressing', 'Typical portion',
     236, 59, 15, 5, 100, NOW(), NOW(), NULL, 1),

    (419, 'srv-caesar-001', 'SRVCAE', 'Caesar Dressing Serving', 'Dressing', 'Creamy',
     'Standard serving for caesar dressing', 'Typical portion',
     236, 59, 15, 5, 100, NOW(), NOW(), NULL, 1),

    (420, 'srv-olive-001', 'SRVOLI', 'Olive Oil Serving', 'Dressing', 'Oil',
     'Standard serving for olive oil', 'Pure oil',
     216, 54, 14, 5, 100, NOW(), NOW(), NULL, 1);
