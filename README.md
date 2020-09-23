# telenor-product-filtering

## Compilation

You require the following:

- Docker

Run the following:

- `docker build -t proximyst/telenor-product-filtering:latest .`

## Running

You require the following:

- Docker
- A built image of the service
- (Optional) A data.csv file at `/data/data.csv`

Run the following:

- `docker run --rm -p "8080:8080" -it proximyst/telenor-product-filtering:latest`

You should now be able to access it at `http://localhost:8080/product`