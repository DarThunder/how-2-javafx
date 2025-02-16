$imageName = "how2javafx"

cd how2javafx

docker info > $null 2>&1
if ($?) {
    Write-Host "Docker está funcionando correctamente."
} else {
    Write-Host "Docker no está funcionando. Por favor, asegúrate de que Docker esté instalado y en ejecución."
    exit 1
}

Write-Host "Construyendo la imagen Docker..."
docker build -f Dockerfile-Windows -t $imageName .

if ($?) {
    Write-Host "Imagen construida correctamente."
} else {
    Write-Host "Hubo un error al construir la imagen."
    exit 1
}
