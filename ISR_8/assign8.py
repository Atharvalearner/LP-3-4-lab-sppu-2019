import requests

city = input("Enter the city name: ")
get_weather(city)

def get_weather(city):
    api_key = "YOUR_API_KEY"  # Replace with your actual API key
    base_url = "http://api.openweathermap.org/data/2.5/weather"
    params = {    # Parameters for the API request
        "q": city,
        "appid": api_key,
        "units": "metric"  # Use "metric" for Celsius or "imperial" for Fahrenheit
    }
    response = requests.get(base_url, params=params)    # Make the request to OpenWeatherMap
    
    if response.status_code == 200:                     # Check if the request was successful
        data = response.json()
        temperature = data['main']['temp']
        wind_speed = data['wind']['speed']
        description = data['weather'][0]['description']
        weather = data['weather'][0]['main']
        print(f"City: {city}")                      # Print the weather information
        print(f"Temperature: {temperature}°C")
        print(f"Wind Speed: {wind_speed} m/s")
        print(f"Description: {description.capitalize()}")
        print(f"Weather: {weather}")
    else:
        print(f"City '{city}' not found or an error occurred.")