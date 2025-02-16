$repoPath = "."
$branchName = "main"

Write-Host "Cambiando al directorio del repositorio..."
Set-Location -Path $repoPath

$changes = git status --porcelain
if (-not $changes) {
    Write-Host "No hay cambios para hacer commit."
    exit 0
}

$commitMessage = Read-Host "Escribe el mensaje del commit"

Write-Host "Agregando cambios al índice..."
git add .

Write-Host "Realizando commit..."
git commit -m $commitMessage

Write-Host "Obteniendo cambios remotos..."
git pull origin $branchName

Write-Host "Subiendo cambios a GitHub..."
git push origin $branchName

Write-Host "Actualización completada con éxito."
