

<h1>🍽️ Meals Planner App</h1>

<p>
    <strong>Meals Planner</strong> is a modern Android application that helps users discover meals, plan weekly meals, and track favorites. 
    It provides a clean interface for browsing meals, adding them to a weekly planner, and managing favorites, with offline handling and connectivity awareness.
</p>

<h2>📌 Features</h2>
<ul>
    <li><strong>Meal Discovery:</strong> Browse meals by name, country, or category.</li>
    <li><strong>Search & Filter:</strong> Filter meals using country and category.</li>
    <li><strong>Meal Details:</strong> View meal details with images, instructions, and ingredients.</li>
    <li><strong>Favorites:</strong> Mark meals as favorite and manage them.</li>
    <li><strong>Weekly Planner:</strong> Add meals to specific days of the week.</li>
    <li><strong>Connectivity Awareness:</strong> App detects network state and displays offline UI when necessary.</li>
    <li><strong>Reactive Architecture:</strong> Built with RxJava3 for reactive data flow.</li>
    <li><strong>Clean Architecture:</strong> Separation of Presentation, Domain, and Data layers.</li>
</ul>

<h2>🏗️ Architecture</h2>
<p>The app follows <strong>Clean Architecture</strong> principles:</p>
<ul>
    <li><strong>Presentation Layer:</strong> Fragments, Adapters, and Presenters using MVP pattern.</li>
    <li><strong>Domain Layer:</strong> Data models (<code>Meal</code>, <code>PlannedMealEntity</code>, <code>DayModel</code>) and business logic.</li>
    <li><strong>Data Layer:</strong> Repository pattern, local database (Room), remote data sources (API).</li>
    <li><strong>Utilities:</strong> RxJava3 for reactive programming, Glide for image loading, Material Components for UI.</li>
</ul>

<h3>Key Libraries Used:</h3>
<ul>
    <li><strong>RxJava3</strong> – Reactive programming</li>
    <li><strong>Glide</strong> – Image loading</li>
    <li><strong>Room</strong> – Local database</li>
    <li><strong>Material Components</strong> – Modern UI elements</li>
    <li><strong>Navigation Component</strong> – Fragment navigation</li>
</ul>

<h2>🔧 Usage</h2>
<ul>
    <li><strong>Home / Planner:</strong> View your weekly meals. Tap a day to see meals planned.</li>
    <li><strong>Search:</strong> Search for meals by name or filter by country/category.</li>
    <li><strong>Meal Details:</strong> Tap on a meal to see details and add to favorites or planner.</li>
    <li><strong>Favorites:</strong> Manage all favorite meals.</li>
    <li><strong>Offline Handling:</strong> When internet is lost, the app displays a "No Internet" overlay.</li>
</ul>

</body>
</html>
