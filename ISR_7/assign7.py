import requests
from bs4 import BeautifulSoup
products = []
urls = ["https://www.scrapingcourse.com/ecommerce/"]    # Initialize the list of discovered URLs with the first page to visit
while len(urls) != 0:           # Until all pages have been visited
    current_url = urls.pop()    # Get the page to visit from the list
    response = requests.get(current_url)
    soup = BeautifulSoup(response.content, "html.parser")

    product_items = soup.select('li[data-products="item"]')
    for item in product_items:
        product = {}
        product["url"] = item.find("a", class_="woocommerce-LoopProduct-link")["href"]        # Get product URL
        product["image"] = item.find("img", class_="product-image")["src"]                  # Get product image
        product["name"] = item.find("h2", class_="product-name").get_text(strip=True)        # Get product name
        price_element = item.find("span", class_="product-price")                            # Get product price
        product["price"] = price_element.get_text(strip=True) if price_element else "N/A"
        products.append(product)                                                             # Append to products list

print(products)