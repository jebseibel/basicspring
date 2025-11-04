# CPSS - Custom Profile Salad System

A full-stack application for managing salad ingredients with nutritional data and building custom salads with real-time nutritional analysis.

## 📋 Overview

CPSS is a monorepo containing both backend and frontend applications:

- **Backend**: Spring Boot REST API (Java 21)
- **Frontend**: React + TypeScript + Tailwind CSS

## 🏗️ Project Structure

```
cpss/
├── src/                    # Spring Boot backend (Java)
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   └── test/
├── frontend/               # React frontend (TypeScript)
│   ├── src/
│   │   ├── components/
│   │   ├── pages/
│   │   ├── services/
│   │   └── types/
│   └── package.json
├── build.gradle.kts        # Gradle build configuration
└── README.md
```

## 🚀 Quick Start

### Prerequisites

- **Java 21** or higher
- **Gradle** (included via wrapper)
- **Node.js 18+** and **npm**
- **MySQL** database

### Backend Setup

1. **Configure database** in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/cpss
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

2. **Run the backend**:
   ```bash
   ./gradlew bootRun
   ```

   Backend will start on `http://localhost:8080`

   **Note**: On first run, Liquibase will automatically create tables and load seed data with 20 food items!

3. **API Documentation** available at:
   - Swagger UI: `http://localhost:8080/swagger-ui/index.html`
   - OpenAPI JSON: `http://localhost:8080/v3/api-docs`

### Frontend Setup

1. **Navigate to frontend directory**:
   ```bash
   cd frontend/
   ```

2. **Install dependencies**:
   ```bash
   npm install
   ```

3. **Start the dev server**:
   ```bash
   npm run dev
   ```

   Frontend will start on `http://localhost:5173`

4. **Open in browser**:
   ```
   http://localhost:5173
   ```

## 🌱 Seed Data

The application comes pre-loaded with **20 food items** including:
- 4 Leafy Greens (Romaine, Spinach, Kale, Arugula)
- 5 Vegetables (Tomatoes, Cucumber, Carrots, Bell Peppers, Red Onion)
- 4 Proteins (Chicken, Eggs, Chickpeas, Feta)
- 3 Nuts & Seeds (Almonds, Walnuts, Sunflower Seeds)
- 4 Dressings (Balsamic, Ranch, Caesar, Olive Oil)

Each food item includes complete nutritional data, flavor profiles, and serving sizes.

See **[SEED_DATA.md](SEED_DATA.md)** for complete details and sample salad recipes!

## 🎯 Features

### Salad Builder
- Interactive ingredient selector
- Real-time nutritional calculation
- Flavor profile visualization (crunch, punch, sweet, savory)
- Aggregated nutrition totals (carbs, fat, protein, sugar)

### Food Management
- CRUD operations for food items
- Nutritional data per food
- Flavor profiles per food
- Serving size information

### Data Management
- Nutrition profiles
- Flavor profiles
- Serving sizes
- User profiles
- Company information

## 🛠️ Tech Stack

### Backend
- **Framework**: Spring Boot 3.5.7
- **Language**: Java 21
- **Database**: MySQL
- **Migration**: Liquibase
- **API Docs**: SpringDoc OpenAPI (Swagger)
- **Build Tool**: Gradle

### Frontend
- **Framework**: React 18
- **Language**: TypeScript
- **Styling**: Tailwind CSS
- **Build Tool**: Vite
- **Routing**: React Router v7
- **Data Fetching**: TanStack Query (React Query)
- **HTTP Client**: Axios
- **Charts**: Recharts
- **Forms**: React Hook Form + Zod
- **Icons**: Lucide React

## 📡 API Endpoints

### Food
- `GET /api/food` - Get all foods
- `GET /api/food/{extid}` - Get food by ID
- `POST /api/food` - Create food
- `PUT /api/food/{extid}` - Update food
- `DELETE /api/food/{extid}` - Delete food

### Salad Builder
- `POST /api/salad/build` - Build salad with nutritional analysis

### Nutrition
- `GET /api/nutrition` - Get all nutrition profiles
- `GET /api/nutrition/{extid}` - Get nutrition by ID
- `POST /api/nutrition` - Create nutrition profile
- `PUT /api/nutrition/{extid}` - Update nutrition
- `DELETE /api/nutrition/{extid}` - Delete nutrition

### Flavor
- `GET /api/flavor` - Get all flavors
- `GET /api/flavor/{extid}` - Get flavor by ID
- `POST /api/flavor` - Create flavor
- `PUT /api/flavor/{extid}` - Update flavor
- `DELETE /api/flavor/{extid}` - Delete flavor

### Serving
- `GET /api/serving` - Get all servings
- `GET /api/serving/{extid}` - Get serving by ID
- `POST /api/serving` - Create serving
- `PUT /api/serving/{extid}` - Update serving
- `DELETE /api/serving/{extid}` - Delete serving

### Profile & Company
- Similar CRUD endpoints with pagination support

## 🧪 Testing

### Backend Tests
```bash
./gradlew test
```

### Frontend Tests (when added)
```bash
cd frontend/
npm test
```

## 🔧 Development

### Running Both Applications

**Terminal 1 - Backend**:
```bash
./gradlew bootRun
```

**Terminal 2 - Frontend**:
```bash
cd frontend/
npm run dev
```

### Building for Production

**Backend**:
```bash
./gradlew build
```
Output: `build/libs/*.jar`

**Frontend**:
```bash
cd frontend/
npm run build
```
Output: `frontend/dist/`

## 🌐 CORS Configuration

CORS is configured in the backend to allow requests from:
- `http://localhost:5173` (Vite dev server)
- `http://localhost:3000` (Alternative dev port)

See `src/main/java/com/seibel/cpss/config/WebConfig.java` to modify.

## 📱 IntelliJ IDEA Setup

1. Open the **root `cpss/` directory** in IntelliJ
2. IntelliJ will automatically detect:
   - Gradle project (backend)
   - Node.js project (frontend)
3. You can run both backend and frontend from the IDE
4. Git integration works for the entire monorepo

## 🤝 Contributing

1. Clone the repository
2. Create a feature branch
3. Make your changes
4. Test both backend and frontend
5. Submit a pull request

## 📝 License

[Add your license here]

## 🐛 Issues

Report issues on the project issue tracker.

---

**Happy Salad Building!** 🥗
