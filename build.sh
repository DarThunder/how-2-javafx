IMAGE_NAME="how-2-javafx"
CONTAINER_NAME="how-2-javafx-container"

cd how2javafx

echo "Construyendo la imagen de Docker..."
sudo docker build -f Dockerfile-Arch -t "$IMAGE_NAME" .

if sudo docker ps -a --format '{{.Names}}' | grep -q "^$CONTAINER_NAME$"; then
    echo "El contenedor ya existe. Eliminándolo..."
    sudo docker rm -f "$CONTAINER_NAME"
fi

echo "Ejecutando el contenedor..."
xhost +local:docker
sudo docker run -e DISPLAY=$DISPLAY -e GDK_BACKEND=wayland -v /tmp/.X11-unix:/tmp/.X11-unix --rm --name "$CONTAINER_NAME" "$IMAGE_NAME"

cd ..
