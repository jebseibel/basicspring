## Project Overview

I have a complete Java Spring Gradle REST API application for a Salad Maker. I need to build a basic frontend website that integrates into the same repository.

## Application Description

- **Purpose**: Salad maker application with ingredient management
- **Backend**: Java Spring with Gradle
- **Data**: Lists of ingredients organized by category and subcategory

## Required Pages

### 1. Ingredients List Page

- Display all ingredients with metadata
- Search functionality
- Filter by category/subcategory
- Pagination support
- Clean, readable list view

### 2. Make a Salad Page (Interactive)

- Interactive ingredient selection
- Visual feedback as ingredients are added
- Display selected ingredients
- Show nutritional information or other aggregated data
- Ability to remove ingredients
- Save/submit the salad

## Technical Requirements

### Architecture

- **Integration**: Frontend should be integrated into the Spring Boot application
- **Location**: Static files should go in `src/main/resources/static/`
- **Technology**: HTML, CSS, and vanilla JavaScript (no frameworks needed for basic version)
- **API Communication**: Frontend will call the existing REST API endpoints

### Project Structure

```
salad-maker/
├── src/main/
│   ├── java/
│   ├── resources/
│   │   ├── static/
│   │   │   ├── index.html
│   │   │   ├── ingredients.html
│   │   │   ├── make-salad.html
│   │   │   ├── css/
│   │   │   │   └── styles.css
│   │   │   └── js/
│   │   │       ├── ingredients.js
│   │   │       ├── make-salad.js
│   │   │       └── api.js
│   │   └── application.properties
├── build.gradle
└── README.md
```

## API Information Needed

Please help me identify and document:

1. **Base URL**: What is the base URL for the API? (e.g., `/api` or `/api/v1`)

2. **Ingredient Endpoints**:

    - List all ingredients: `GET /ingredients`
    - Get single ingredient: `GET /ingredients/{id}`
    - Search/filter: What parameters are supported?
    - Pagination: What parameters? (e.g., `page`, `size`, `sort`)
3. **Ingredient Data Structure**: What does an ingredient object look like? Example:

    ```json
    {
      "id": 1,
      "name": "Tomato",
      "category": "Vegetable",
      "subcategory": "Red Vegetables",
      "calories": 20,
      "price": 0.50,
      "description": "Fresh red tomatoes"
    }
    ```

4. **Categories**: How do I get the list of categories and subcategories?

5. **Salad Creation**: Is there an endpoint to save/create a salad? What does it expect?


## Features to Implement

### Ingredients List Page

- Table/card view of ingredients
- Search bar (search by name)
- Category filter dropdown
- Subcategory filter dropdown
- Pagination controls (Previous/Next, page numbers)
- Click ingredient to see details
- Responsive design

### Make a Salad Page

- Browse/search ingredients
- Add ingredients to salad (with visual feedback)
- Display current salad composition
- Show totals (calories, price, etc.)
- Remove ingredients from salad
- Clear all
- Save/submit salad
- Responsive design

## Design Preferences

- Clean, modern look
- Simple color scheme (can use greens/fresh colors for salad theme)
- Mobile-friendly
- Easy to navigate
- Clear call-to-action buttons

## Questions to Answer

Before starting implementation, please help me:

1. Scan my Spring Boot project to identify the REST API endpoints
2. Determine the data structure of ingredients
3. Check if pagination is already implemented and what parameters it uses
4. Identify any existing CORS configuration
5. Recommend the best approach for integrating the frontend

## Next Steps

Once we have the API details:

1. Create the HTML structure for both pages
2. Implement CSS styling
3. Write JavaScript for API calls and interactivity
4. Test the integration
5. Add error handling and loading states

## Notes

- Keep it simple and functional first
- Can enhance with frameworks (React, Vue) later if needed
- Focus on core CRUD operations with good UX
- Ensure proper error handling for API calls