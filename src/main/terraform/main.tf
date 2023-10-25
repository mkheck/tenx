provider "azurerm" {
  features {}
}

resource "azurerm_resource_group" "tenx" {
  name     = "mkheck-tenx-rg"
  location = "eastus"
}

resource "azurerm_spring_cloud_service" "tenx" {
  name                = "mkheck-tenx-asa"
  resource_group_name = azurerm_resource_group.tenx.name
  location            = azurerm_resource_group.tenx.location
}

resource "azurerm_spring_cloud_app" "tenx" {
  name                = "mkheck-tenx-app"
  resource_group_name = azurerm_resource_group.tenx.name
  service_name        = azurerm_spring_cloud_service.tenx.name

  identity {
    type = "SystemAssigned"
  }
}

resource "azurerm_spring_cloud_java_deployment" "tenx" {
  name                = "default"
  spring_cloud_app_id = azurerm_spring_cloud_app.tenx.id
  instance_count      = 1
  jvm_options         = "-Xms2048m -Xmx2048m"

  quota {
    cpu    = "1"
    memory = "2Gi"
  }

  runtime_version = "Java_17"
}