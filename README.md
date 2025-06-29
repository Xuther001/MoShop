# MoShop Setup & Usage Guide

Backend (MoShop) Setup

1. Clone these Repositories: git clone https://github.com/Xuther001/MoShopFE.git git clone https://github.com/Xuther001/MoShop.git

2. **Open the MoShop project** in IntelliJ IDEA or your preferred IDE.

3. **Update your `application.properties`** (or `.yml`) with your PostgreSQL configuration:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/your-database-name
   spring.datasource.driver-class-name=org.postgresql.Driver
   spring.sql.init.platform=postgres
   spring.datasource.username=your-username
   spring.datasource.password=your-password
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

4. Inject the database data:

Connect to your PostgreSQL database (your-database-name).

Run the following SQL statements to insert categories:

<pre><code>
INSERT INTO categories (name, description) VALUES
('Electronics', 'Devices, gadgets, and accessories for everyday use, including mobile phones, laptops, cameras, and more.'),
('Clothing', 'Apparel for men, women, and children, featuring everything from casual wear to formal attire and accessories.'),
('Home & Kitchen', 'Products for home improvement, kitchen appliances, furniture, and decor to enhance living spaces.'),
('Sports & Outdoors', 'Equipment, clothing, and accessories for sports, fitness, and outdoor adventures, from cycling gear to hiking essentials.'),
('Beauty & Personal Care', 'Skincare, makeup, grooming tools, and health products to support self-care and personal wellness.'),
('Toys & Games', 'A wide range of toys, board games, video games, and educational products for children and adults.'),
('Books & Stationery', 'A vast selection of books across genres, along with office supplies, planners, and writing tools.'),
('Automotive', 'Car parts, accessories, and tools for vehicle maintenance and enhancement.'),
('Health & Wellness', 'Health supplements, fitness gear, and medical equipment for personal health management and wellbeing.'),
('Jewelry & Watches', 'Elegant watches, fine jewelry, and accessories to complement every style and occasion.'),
('Footwear', 'Shoes, sandals, and boots for men, women, and children, designed for comfort, style, and functionality.'),
('Office Supplies', 'Essentials for the office or home workspace, including printers, furniture, and organizational tools.'),
('Groceries & Gourmet Foods', 'Daily essentials, snacks, beverages, and gourmet foods from local and international brands.'),
('Baby Products', 'Everything for infants and toddlers, from clothing and toys to baby care products and accessories.'),
('Pet Supplies', 'Food, toys, and care essentials for cats, dogs, birds, and other pets.'),
('Travel & Luggage', 'Luggage, backpacks, and travel accessories for all your trips, from weekend getaways to international adventures.'),
('Music & Instruments', 'Instruments, audio equipment, and accessories for musicians and audiophiles.'),
('Garden & Outdoor', 'Gardening tools, outdoor furniture, and landscaping accessories to create and maintain beautiful outdoor spaces.'),
('Health & Household', 'Cleaning supplies, household essentials, and health-related products for everyday use.'),
('Art & Craft Supplies', 'Materials for artists and hobbyists, including paints, canvases, and crafting tools.'),
('Eyewear', 'A collection of stylish and functional glasses, including prescription eyewear, sunglasses, and protective lenses for fashion and vision care.');
</code></pre>

Then, insert the products:

<pre><code>
INSERT INTO products (category_id, name, image_url, description, stock, price) VALUES
(21, 'Ray-Ban Aviator Classic', 'https://i.imgur.com/PW4PQaY.jpeg', 'Ray-Ban''s Aviator Classic sunglasses are a timeless model originally designed for U.S. aviators in 1937...', 50, 150.00),
(21, 'Oakley Holbrook', 'https://i.imgur.com/ijT2KzR.jpeg', 'The Oakley Holbrook sunglasses offer a blend of vintage style and modern technology...', 40, 120.00),
(21, 'Warby Parker Durand', 'https://i.imgur.com/DlMRgYt.jpeg', 'The Warby Parker Durand glasses feature a bold, acetate frame with square lenses...', 60, 95.00),
(21, 'Gucci GG0061S', 'https://i.imgur.com/7bvG4Rt.jpeg', 'The Gucci GG0061S sunglasses feature a bold, oversized square design...', 30, 300.00),
(21, 'Persol PO0649', 'https://i.imgur.com/PW4PQaY.jpeg', 'Persol''s PO0649 sunglasses are handcrafted with Italian acetate...', 25, 220.00),
(21, 'Maui Jim Red Sands', 'https://i.imgur.com/TXRVwxn.jpeg', 'The Maui Jim Red Sands sunglasses offer polarized lenses with superior clarity...', 45, 180.00),
(2, 'Lee Men''s Extreme Motion Flat Front Slim Straight Pant', 'https://i.imgur.com/3ZMpvkQ.jpeg', 'SLIM FIT. With a slim fit through the hip and thigh...', 100, 50.00),
(2, 'Lee Men''s Extreme Motion Straight Taper Jean', 'https://i.imgur.com/WmI8mGv.jpeg', 'STRAIGHT FIT. With a straight fit through the hip and thigh...', 100, 20.00),
(2, 'Lee Men''s Extreme Motion Athletic Taper Jean', 'https://i.imgur.com/2FmQRe0.jpeg', 'ATHLETIC FIT. With a tapered leg and fitted waist...', 100, 15.00),
(2, 'Calvin Klein Women''s Classic Fit Straight Lux Suit Skirt', 'https://i.imgur.com/H6U3Bis.jpeg', 'Universal Match: Transition effortlessly through the seasons...', 100, 25.00),
(2, 'AEROPOSTALE Women''s Aero High Waisted Jegging', 'https://i.imgur.com/0y4LxEO.jpeg', '4-way stretch denim (9.6 OZ). Allows more freedom of movement', 100, 45.00),
(2, 'Democracy Women''s Ab Solution Jegging', 'https://i.imgur.com/ACbM3oS.jpeg', 'Democracy’s signature “Ab”solution jean uses innovative construction...', 100, 15.00),
(11, 'Nike Air Max 270', 'https://i.imgur.com/dr0YiZp.jpeg', 'The Nike Air Max 270 is a stylish sneaker that combines comfort...', 50, 150.00),
(11, 'Adidas Ultraboost 21', 'https://i.imgur.com/ANDQ2O6.jpeg', 'The Adidas Ultraboost 21 offers a responsive Boost midsole...', 30, 180.00),
(11, 'Puma RS-X', 'https://i.imgur.com/4agk5hI.jpeg', 'The Puma RS-X is a retro-inspired sneaker featuring a chunky silhouette...', 40, 110.00),
(11, 'New Balance 990v5', 'https://i.imgur.com/jd3KSjf.jpeg', 'The New Balance 990v5 is an iconic running shoe that blends premium materials...', 25, 175.00),
(11, 'ASICS Gel-Kayano 27', 'https://i.imgur.com/f1bwjIr.jpeg', 'The ASICS Gel-Kayano 27 is designed for stability and support...', 60, 160.00),
(11, 'Reebok Classic Leather', 'https://i.imgur.com/7sRY8Wj.jpeg', 'The Reebok Classic Leather is a timeless sneaker known for its clean lines...', 70, 75.00);
</code></pre>


Frontend (MoShopFE) Setup

Open the MoShopFE project in Visual Studio Code or your favorite IDE.

Install dependencies:

npm install
Start the development server:

npm start
Open your browser and go to:

http://localhost:3000/

Create an account by clicking Sign Up and filling out the registration form.

Log in with your new username and password.
