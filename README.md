<div id="top"></div>
<div align="center">
<h1>📰 NewsAndFeed – Kotlin Multiplatform</h1>
<em>Built with the tools and technologies:</em><br><br>
<img src="https://img.shields.io/badge/Kotlin-7F52FF.svg?style=default&logo=Kotlin&logoColor=white" alt="Kotlin"/>
<img src="https://img.shields.io/badge/Kotlin%20Multiplatform-7F52FF?logo=kotlin&logoColor=white"/>
<img src="https://img.shields.io/badge/Ktor-087CFA?logo=ktor&logoColor=white"/>
</div>

## 📌 Sobre o Projeto
NewsAndFeed é um projeto Kotlin Multiplatform (KMP) focado em compartilhamento de código entre Android e iOS, centralizando regras de negócio, dados e infraestrutura no commonMain. O projeto tem como objetivo estudar e aplicar arquitetura em projetos KMP reais, priorizando separação de responsabilidades, reutilização de código e independência de plataforma. Android e iOS atuam exclusivamente como camadas de apresentação.

## 🎯 Objetivos
Compartilhar regras de negócio entre Android e iOS, aplicar arquitetura inspirada em Clean Architecture, utilizar bibliotecas compatíveis com KMP, manter código desacoplado de plataforma e servir como base educacional e projeto realista. Este projeto segue uma variação pragmática da Clean Architecture, comum em projetos profissionais e educacionais em KMP.

## 🧱 Estrutura Geral (commonMain)

newsandfeed
├── data
├── di
├── domain
├── infra
├── ui
└── util


## ❤️ Domain (Regra de Negócio)
A camada domain concentra as regras de negócio, contratos e entidades centrais do sistema.

domain
├── entity
├── mapper
├── repository
└── usecase


Modelos puros do domínio:
```kotlin
data class ArticleEntity(
    val title: String,
    val description: String
)

Wrapper de estado:

sealed class DataOrException<T> {
    data class Success<T>(val data: T) : DataOrException<T>()
    data class Error(val exception: Exception) : DataOrException<Nothing>()
}

Contrato do repositório:

interface ArticleRepository {
    suspend fun getArticles(): DataOrException<List<ArticleEntity>>
}

Use case:

class GetArticlesUseCase(
    private val repository: ArticleRepository
) {
    suspend operator fun invoke() = repository.getArticles()
}

Mappers são responsáveis por converter DTOs em Entities, evitando acoplamento do domínio com detalhes externos.
💾 Data (Acesso a Dados)

Camada responsável por buscar, tratar e preparar dados.

data
├── dto
├── remote
└── repository

DTOs representam respostas da API e não devem ser utilizados diretamente na UI ou no Domain.

Contrato da fonte remota:

interface RemoteDataSource {
    suspend fun fetchNews(): NewsDto
}

Implementação do repositório:

class ArticleRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : ArticleRepository {
    override suspend fun getArticles(): DataOrException<List<ArticleEntity>> {
        // chamada remota
        // mapeamento DTO -> Entity
        // tratamento de erro
    }
}

🌐 Infra (Infraestrutura)

Camada responsável por implementações técnicas concretas.

infra
└── remote

Configuração do Ktor Client e implementação real da API:

class KtorApiImpl : KtorApi {
    override suspend fun getNews(): NewsDto {
        // implementação real
    }
}

🧩 DI (Dependency Injection)

Responsável por criar instâncias, resolver dependências e compartilhar objetos entre plataformas.

di
├── CommonModule.kt
└── DriverSqlModule.kt

val commonModule = module {
    single<ArticleRepository> { ArticleRepositoryImpl(get()) }
}

🧰 Util

util
├── CFlow.kt
└── CoroutineViewModel.kt

Fornece abstrações para uso de Flow no iOS e uma base de ViewModel multiplataforma, comuns em projetos KMP com StateFlow.
🚀 Como Iniciar um Projeto KMP

Criar o projeto usando Kotlin Multiplatform Wizard ou template do Android Studio. Definir responsabilidades por camada: commonMain para negócio, dados e infra; androidMain para UI Android; iosMain para UI iOS. Utilizar bibliotecas compatíveis como Ktor, SQLDelight, Coroutines e Flow. Evitar no commonMain Context Android, frameworks de UI nativos e código dependente de plataforma.
📚 Pré-requisitos

Kotlin básico a intermediário, Coroutines e Flow, conceitos de Clean Architecture, noções de Kotlin Multiplatform, injeção de dependência e consumo de API REST.
✅ Benefícios da Arquitetura

Código realmente compartilhado, separação clara de responsabilidades, fácil manutenção e evolução, arquitetura alinhada ao ecossistema KMP.
📌 Considerações Finais

Este projeto representa um estado realista e evolutivo de um projeto Kotlin Multiplatform. A arquitetura pode evoluir para uma Clean Architecture mais rígida, mas já segue boas práticas utilizadas em projetos KMP reais.
---

📚 Projeto com foco educacional em **Kotlin Multiplatform na prática**.corrige para mim
