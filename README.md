# DriveFlow - Aplicativo de Taxi

## Descrição do Projeto

O **DriveFlow** é um aplicativo de taxi projetado para facilitar a solicitação de viagens e fornecer estimativas de trajetos com base em diferentes opções de motoristas. A proposta é oferecer uma interface intuitiva para visualizar rotas, avaliar opções de motoristas e confirmar viagens rapidamente.

---

## Status do Projeto

:warning: **Este projeto está atualmente incompleto.**

O desenvolvimento foi interrompido devido a:

1. **Problemas de conectividade**: Quedas de internet que duraram mais de 4 dias.
2. **Acesso restrito à API do Google**: A falta de acesso à API do Google impediu a implementação e os testes com mapas e rotas dinâmicas.

Apesar das adversidades, a estrutura principal do projeto foi estabelecida, incluindo:

- **Arquitetura MVVM** (Model-View-ViewModel)
- Componentes de interface em Jetpack Compose
- ViewModels para gerenciar estado e lógica de negócio
- Mock de dados para simular opções de motoristas

---

## Estrutura do Projeto

```
DriveFlow/
└── src/
    └── main/
        └── java/
            └── br/com/samuelborges/driveflow/
                └── ui/
                    └── MainActivity.kt

```

---

## Tecnologias Utilizadas

- **Kotlin**
- **Jetpack Compose**
- **ViewModel**
- **StateFlow**
- **Material3**

---

## Como Executar o Projeto

1. **Clone o repositório**:

   ```bash
   git clone https://github.com/seu-usuario/driveflow.git
   ```

2. **Abra no Android Studio**.

3. **Execute o app** em um emulador ou dispositivo físico.

4. **Configuração da API Key do Google**:
   - A chave da API do Google Maps está localizada no arquivo `MainActivity.kt`.

---

## Observações Finais

Este projeto foi desenvolvido como parte de um teste técnico para uma empresa. Infelizmente, não foi concluído devido aos problemas mencionados anteriormente.

---

## Contato

**Desenvolvedor**: Samuel Borges  
**Email**: samuel.borges@email.com
