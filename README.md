<div id="top">

<!-- HEADER STYLE: CONSOLE -->
<div align="center">

</div>

<!-- BADGES -->
<!-- local repository, no metadata badges. -->

<em>Built with the tools and technologies:</em>

<img src="https://img.shields.io/badge/Kotlin-7F52FF.svg?style=default&logo=Kotlin&logoColor=white" alt="Kotlin">

</div>
<br>

## ⚛️ Table of Contents

- [⚛ ️ Table of Contents](#-table-of-contents)
- [🔮 Overview](#-overview)
- [💫 Features](#-features)
- [🌌 Project Structure](#-project-structure)
    - [✨ Project Index](#-project-index)
- [⚡ Getting Started](#-getting-started)
    - [💠 Prerequisites](#-prerequisites)
    - [🔷 Installation](#-installation)
    - [🔹 Usage](#-usage)
    - [🔸 Testing](#-testing)
- [🌀 Roadmap](#-roadmap)
- [✴ ️ Contributing](#-contributing)
- [⭐ License](#-license)
- [✧ Acknowledgments](#-acknowledgments)

---

## 🔮 Overview



---

## 💫 Features

<code>❯ REPLACE-ME</code>

---

## 🌌 Project Structure

```sh
# 📰 NewsAndFeed – Kotlin Multiplatform

Este projeto é um **Kotlin Multiplatform (KMP)** com foco em **compartilhamento de código entre Android e iOS**, onde o `commonMain` concentra **regra de negócio, dados e infraestrutura compartilhada**.

O objetivo principal é estudar e aplicar **arquitetura em projetos KMP reais**, integrando **Ktor, repositórios, use cases e DI**, mantendo o máximo possível fora das camadas específicas de plataforma.

---

## 🎯 Visão Geral do Projeto

* Código compartilhado em `commonMain`
* Android e iOS atuam como **camadas de apresentação**
* Comunicação com API via **Ktor**
* Arquitetura inspirada em **Clean Architecture**, adaptada à realidade do KMP

> 📌 Nem todo projeto KMP segue Clean Architecture “pura”. Este projeto segue uma **variação pragmática**, comum em projetos educacionais e reais.

---

## 🧱 Estrutura Geral do `commonMain`

```text
newsandfeed
├── data
├── di
├── domain
├── infra
├── ui
└── util
```

Cada pacote possui uma **responsabilidade clara**, explicada abaixo.

---

## ❤️ Domain (Regra de Negócio)

A camada **domain** concentra o que é mais importante no projeto: **as regras de negócio**.

```text
domain
├── entity
├── mapper
├── repository
└── usecase
```

### 📁 `entity`

* Modelos centrais do domínio
* Independentes de framework

```kotlin
data class ArticleEntity(
    val title: String,
    val description: String
)
```

Também inclui wrappers de estado:

```kotlin
sealed class DataOrException<T> {
    data class Success<T>(val data: T) : DataOrException<T>()
    data class Error(val exception: Exception) : DataOrException<Nothing>()
}
```

---

### 📁 `repository`

Define **contratos**, nunca implementações:

```kotlin
interface ArticleRepository {
    suspend fun getArticles(): DataOrException<List<ArticleEntity>>
}
```

---

### 📁 `usecase`

Use cases encapsulam **ações do negócio**.

```text
usecase
└── article
```

Exemplo conceitual:

```kotlin
class GetArticlesUseCase(
    private val repository: ArticleRepository
) {
    suspend operator fun invoke() = repository.getArticles()
}
```

---

### 📁 `mapper`

Responsável por converter modelos entre camadas:

* DTO → Entity

Isso evita acoplamento do domínio com a API.

---

## 💾 Data (Acesso a Dados)

Camada responsável por **buscar e preparar dados**.

```text
data
├── dto
├── remote
└── repository
```

### 📁 `dto`

Modelos que representam **respostas da API**:

* `ArticleDto`
* `NewsDto`
* `SourceDto`

> ❌ DTOs não devem ser usados diretamente na UI ou no Domain.

---

### 📁 `remote`

Define contratos para fontes de dados remotas:

```kotlin
interface RemoteDataSource {
    suspend fun fetchNews(): NewsDto
}
```

---

### 📁 `repository`

Implementação dos contratos do Domain:

```kotlin
class ArticleRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : ArticleRepository {
    override suspend fun getArticles() = /* lógica */
}
```

Aqui acontece:

* Chamada de API
* Mapeamento DTO → Entity
* Tratamento de erro

---

## 🌐 Infra (Infraestrutura)

A camada **infra** contém implementações técnicas concretas.

```text
infra
└── remote
```

### 📁 `infra/remote`

* Configuração do **Ktor Client**
* Implementação real da API

```kotlin
class KtorApiImpl : KtorApi {
    override suspend fun getNews(): NewsDto { /* ... */ }
}
```

> 📌 Separar `infra` ajuda a manter `data` e `domain` limpos.

---

## 🧩 DI (Dependency Injection)

```text
di
├── CommonModule.kt
└── DriverSqlModule.kt
```

Responsável por:

* Criar instâncias
* Resolver dependências
* Compartilhar objetos entre plataformas

Exemplo conceitual:

```kotlin
val commonModule = module {
    single<ArticleRepository> { ArticleRepositoryImpl(get()) }
}
```

---

## 🖥️ UI Compartilhada (Opcional)

```text
ui
└── features
    └── home
```

Contém:

* ViewModels compartilhados
* Lógica de estado

> ⚠️ Em KMP, UI compartilhada **não é obrigatória**, mas pode ser útil.

---

## 🧰 Util

```text
util
├── CFlow.kt
└── CoroutineViewModel.kt
```

Fornece:

* Abstrações para `Flow` funcionar em iOS
* Base de ViewModel multiplataforma

Esses arquivos são **padrão em projetos KMP com StateFlow**.

---

## 🚀 Como Iniciar um Projeto KMP (Padrão)

### 1️⃣ Criar projeto

* Use o **Kotlin Multiplatform Wizard**
* Ou template do Android Studio

### 2️⃣ Definir responsabilidades

* `commonMain`: negócio, dados, infra compartilhada
* `androidMain`: UI Android
* `iosMain`: UI iOS

### 3️⃣ Escolher bibliotecas compatíveis

* Networking: **Ktor**
* Database: **SQLDelight**
* Async: **Coroutines**

### 4️⃣ Evitar no `commonMain`

* Context Android
* Frameworks de UI nativos
* Código dependente de plataforma

---

## 📚 O Que é Necessário Saber para Usar Este Projeto

* Kotlin básico e intermediário
* Coroutines e Flow
* Conceitos de Clean Architecture
* Noções de KMP
* Injeção de dependência
* API REST

---

## ✅ Benefícios da Arquitetura Atual

✔️ Código compartilhado real
✔️ Separação clara de responsabilidades
✔️ Fácil evolução
✔️ Aderente ao padrão KMP

---

## 📌 Considerações Finais

Esta arquitetura representa um **estado realista** de um projeto KMP em evolução.

> 📈 O projeto pode evoluir para uma Clean Architecture mais rígida, mas já segue boas práticas do ecossistema KMP.

---

📚 Projeto com foco educacional em **Kotlin Multiplatform na prática**.
