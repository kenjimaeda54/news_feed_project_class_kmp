Entendi perfeitamente agora — obrigado por insistir 👍
O que você quer é **UM ARQUIVO README.md válido**, **sem HTML solto**, **sem divisões fora**, **só Markdown puro**, para **copiar e colar e pronto**.

Abaixo está **UM MARKDOWN COMPLETO**, **100% compatível com GitHub**, **sem HTML**, **sem nada fora**.

⬇️ **COPIE TUDO A PARTIR DAQUI** ⬇️

---

# 📰 NewsAndFeed – Kotlin Multiplatform

![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF.svg?style=flat\&logo=Kotlin\&logoColor=white)
![Kotlin Multiplatform](https://img.shields.io/badge/Kotlin%20Multiplatform-7F52FF?logo=kotlin\&logoColor=white)
![Ktor](https://img.shields.io/badge/Ktor-087CFA?logo=ktor\&logoColor=white)

## 📌 Sobre o Projeto

**NewsAndFeed** é um projeto **Kotlin Multiplatform (KMP)** focado em **compartilhamento de código entre Android e iOS**, centralizando **regras de negócio, dados e infraestrutura** no `commonMain`.

O objetivo é aplicar **arquitetura em projetos KMP reais**, priorizando separação de responsabilidades, reutilização de código e independência de plataforma.

Android e iOS atuam exclusivamente como **camadas de apresentação**.

---

## 🎯 Objetivos

* Compartilhar regras de negócio entre Android e iOS
* Aplicar arquitetura inspirada em Clean Architecture
* Utilizar bibliotecas compatíveis com KMP
* Manter código desacoplado de plataforma
* Servir como base educacional e projeto realista

Este projeto segue uma **variação pragmática da Clean Architecture**, comum em projetos profissionais e educacionais em KMP.

---

## 🧱 Estrutura Geral (`commonMain`)

```text
newsandfeed
├── data
├── di
├── domain
├── infra
├── ui
└── util
```

---

## ❤️ Domain (Regra de Negócio)

A camada **domain** concentra as **regras de negócio**, contratos e entidades centrais do sistema.

```text
domain
├── entity
├── mapper
├── repository
└── usecase
```

### Entidades

Modelos puros do domínio, independentes de framework:

```kotlin
data class ArticleEntity(
    val title: String,
    val description: String
)
```

Wrapper de estado:

```kotlin
sealed class DataOrException<T> {
    data class Success<T>(val data: T) : DataOrException<T>()
    data class Error(val exception: Exception) : DataOrException<Nothing>()
}
```

### Repositórios

Contratos definidos no domínio:

```kotlin
interface ArticleRepository {
    suspend fun getArticles(): DataOrException<List<ArticleEntity>>
}
```

### Use Cases

Encapsulam ações do negócio:

```kotlin
class GetArticlesUseCase(
    private val repository: ArticleRepository
) {
    suspend operator fun invoke() = repository.getArticles()
}
```

### Mappers

Responsáveis por converter DTOs em entidades de domínio, evitando acoplamento.

---

## 💾 Data (Acesso a Dados)

Camada responsável por buscar, tratar e preparar dados.

```text
data
├── dto
├── remote
└── repository
```

### DTOs

Modelos que representam respostas da API externa:

* ArticleDto
* NewsDto
* SourceDto

DTOs **não devem** ser usados diretamente na UI ou no Domain.

### Fonte Remota

```kotlin
interface RemoteDataSource {
    suspend fun fetchNews(): NewsDto
}
```

### Implementação do Repositório

```kotlin
class ArticleRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : ArticleRepository {

    override suspend fun getArticles(): DataOrException<List<ArticleEntity>> {
        // chamada de API
        // mapeamento DTO -> Entity
        // tratamento de erro
    }
}
```

---

## 🌐 Infra (Infraestrutura)

Camada responsável por implementações técnicas concretas.

```text
infra
└── remote
```

### API e Networking

```kotlin
class KtorApiImpl : KtorApi {
    override suspend fun getNews(): NewsDto {
        // implementação real
    }
}
```

Separar a camada `infra` mantém `domain` e `data` limpos e desacoplados.

---

## 🧩 Dependency Injection (DI)

```text
di
├── CommonModule.kt
└── DriverSqlModule.kt
```

Responsável por criar instâncias, resolver dependências e compartilhar objetos entre plataformas.

```kotlin
val commonModule = module {
    single<ArticleRepository> { ArticleRepositoryImpl(get()) }
}
```

---

## 🧰 Util

```text
util
├── CFlow.kt
└── CoroutineViewModel.kt
```

Fornece abstrações para permitir uso de `Flow` no iOS e uma base de ViewModel multiplataforma.

---

## 🚀 Como Iniciar um Projeto KMP

1. Criar o projeto com Kotlin Multiplatform Wizard ou Android Studio
2. Definir responsabilidades por camada

   * `commonMain`: negócio, dados e infra
   * `androidMain`: UI Android
   * `iosMain`: UI iOS
3. Bibliotecas recomendadas

   * Ktor
   * SQLDelight
   * Coroutines / Flow
4. Evitar no `commonMain`

   * Context Android
   * Frameworks de UI nativos
   * Código dependente de plataforma

---

## 📚 Pré-requisitos

* Kotlin (básico a intermediário)
* Coroutines e Flow
* Conceitos de Clean Architecture
* Kotlin Multiplatform
* Injeção de dependência
* Consumo de API REST

---

## ✅ Benefícios da Arquitetura

* Código realmente compartilhado
* Separação clara de responsabilidades
* Fácil manutenção e evolução
* Arquitetura alinhada ao ecossistema KMP

---

## 📌 Considerações Finais

Este projeto representa um **estado realista e evolutivo** de um projeto Kotlin Multiplatform.

A arquitetura pode evoluir para uma Clean Architecture mais rígida, mas já segue boas práticas utilizadas em projetos KMP reais.

---

🔗 **Repositório**
[https://github.com/kenjimaeda54/news_feed_project_class_kmp/tree/develop](https://github.com/kenjimaeda54/news_feed_project_class_kmp/tree/develop)

---


