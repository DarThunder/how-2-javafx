repo_path="."
branch_name="main"

echo "Cambiando al directorio del repositorio..."
cd "$repo_path" || { echo "Error: No se pudo acceder al repositorio"; exit 1; }

if git diff --quiet && git diff --staged --quiet; then
    echo "No hay cambios para hacer commit."
    exit 0
fi

read -p "Escribe el mensaje del commit: " commit_message

echo "Agregando cambios al índice..."
git add .

echo "Realizando commit..."
git commit -m "$commit_message"

echo "Obteniendo cambios remotos..."
git pull origin "$branch_name"

echo "Subiendo cambios a GitHub..."
git push origin "$branch_name"

echo "Actualización completada con éxito."
