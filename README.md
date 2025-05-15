# Linguagem Mariago
> Status do projeto: Em andamento

> Esse projeto nos foi proposto no 6ºSemestre na disciplina de Compiladores

> Escrevemos esse projeto juntos durante as aulas

### Tópicos
- Analisador Léxico
- Analisador Sintático
- Analisador Semântico
- Como executar o código
- Exemplos de código

## 🔍 Analisador Léxico
### 🔤 Caracteres
| Token          | Regra Léxica                                                                      | Exemplo     |
|----------------|-----------------------------------------------------------------------------------|-------------|
| **NUM_DECIMAL**| <code>[0-9]<sup>+</sup></code> `'.'` <code>[0-9]<sup>+</sup></code>               | `6.6`       |
| **NUM_INTEIRO**| <code>[0-9]<sup>+</sup></code>                                                    | `6`         |
| **TEXTO**      | `'"'` `CARACTER*` `SYMBOL*` `MATH_OP*` `ASSI_OP*` `COMP_OP*` `LOGI_OP*`  `'"'`    | `"Mariago"` |
| **CARACTER**   | <code>[A-Za-z]<sup>+</sup></code>                                                 | `A`         |

### ⚙️ Operadores
#### 📝 Atribuição
| Token         | Símbolo |
|---------------|---------|
| **ASSI_OP**   | `=`     |

#### 🔍 Comparação
| Token         | Símbolo | Descrição        |
|---------------|---------|------------------|
| **COMP_OP**   | `==`    | Igualdade        |
| **COMP_OP**   | `!=`    | Diferente        |
| **COMP_OP**   | `>=`    | Maior ou igual   |
| **COMP_OP**   | `<=`    | Menor ou igual   |
| **COMP_OP**   | `<`     | Menor            |
| **COMP_OP**   | `>`     | Maior            |

#### 🧠 Lógicos
| Token         | Símbolo  | Descrição   |
|---------------|----------|-------------|
| **LOGI_OP**   | `&&`     | E lógico    |
| **LOGI_OP**   | `\|\|`   | Ou lógico   |

#### ➗ Matemáticos
| Token         | Símbolo | Descrição       |
|---------------|---------|-----------------|
| **MATH_OP**   | `+`     | Soma            |
| **MATH_OP**   | `-`     | Subtração       |
| **MATH_OP**   | `*`     | Multiplicação   |
| **MATH_OP**   | `%`     | Módulo          |
| **MATH_OP**   | `/`     | Divisão         |

### 🔡 Variáveis
| Token         | Regra Léxica                                       | Exemplo |
|---------------|----------------------------------------------------|---------|
| **VARIAVEL**  | <code>[a-z]<sup>+</sup>[A-Za-z]<sup>*</sup></code> |`aA`     |

### 🧩 Símbolos
| Token         | Símbolo |
|---------------|---------|
| **SYMBOL**    | `(`     |
| **SYMBOL**    | `)`     |
| **SYMBOL**    | `[`     |
| **SYMBOL**    | `]`     |
| **SYMBOL**    | `{`     |
| **SYMBOL**    | `}`     |
| **SYMBOL**    | `,`     |
| **SYMBOL**    | `;`     |

### 💬 Comentários
| Token           | Regra Léxica                                                                             |
|-----------------|------------------------------------------------------------------------------------------|
| **COMENTARIO**  | `"_"` `CARACTER*` `SYMBOL*` `MATH_OP*` `ASSI_OP*` `COMP_OP*` `LOGI_OP*` `VARIAVEL*` "_"` |

### 🔑 Palavras Reservadas
#### 🧠 Tipos de Variável
| Token         | Palavra        |
|---------------|----------------|
| **RESERVADA** | `Inteiro`      |
| **RESERVADA** | `Decimal`      |
| **RESERVADA** | `Texto`        |

#### 🗣️ Entrada e Saída de Dados
| Token         | Palavra  | Equivalência |
|---------------|----------|--------------|
| **RESERVADA** | `Insere` | `input`      |
| **RESERVADA** | `Exibir` | `print`      |

#### 🔀 Estrutura Condicional
| Token         | Palavra        |
|---------------|----------------|
| **RESERVADA** | `Quest`        | 
| **RESERVADA** | `Request`      |
| **RESERVADA** | `Si`           |
| **RESERVADA** | `No`           |

#### 🔁 Laços de Repetição
| Token         | Palavra        |
|---------------|----------------|
| **RESERVADA** | `Enlace`       |
| **RESERVADA** | `Roda`         |
| **RESERVADA** | `Quebra`       |
| **RESERVADA** | `Ciclo`        |
| **RESERVADA** | `Atualiza`     |

#### 📦 Arrays
| Token         | Palavra        |
|---------------|----------------|
| **RESERVADA** | `Conjunto`     |
| **RESERVADA** | `Insere`       |
| **RESERVADA** | `Remove`       |
| **RESERVADA** | `Ordenar`      |
| **RESERVADA** | `Dinamico`     |
| **RESERVADA** | `Inicio`       |
| **RESERVADA** | `Final`        |
| **RESERVADA** | `MaiorTo`      |
| **RESERVADA** | `MenorTo`      |

#### 🔧 Função
| Token         | Palavra   |
| --------------| ----------|
| **RESERVADA** | `Destino` |

## 🔎 Analisador Sintático
| 💠 **BLOCO** 💠                                                                                                              |
|-------------------------------------------------------------------------------------------------------------------------------|
| `bloco` → `declaracao bloco` \| `atribuicao bloco` \| `quest bloco` \| `enlace bloco` \| `quebra bloco` \| `ciclo bloco` \| ε |

<!--| 💠 **DECLARAÇÃO DE VARIÁVEIS** 💠                       |
|----------------------------------------------------------|
| `declaracao` → `tipoVar` `VARIAVEL` `valoravel` `;`      |
| `valoravel`  → `'='` `idt` \| ε                          |
| `tipoVar`    → `'Inteiro'` \| `'Decimal'` \| `'Texto'`   |
| `idt`        → `NUM_DECIMAL` \| `NUM_INTEIRO` \| `TEXTO` |
| 💠💠💠💠💠💠💠 **REGRAS LÉXICAS** 💠💠💠💠💠💠💠  |
| `NUM_DECIMAL` → `[0-9]+ '.' [0-9]+`                      |
| `NUM_INTEIRO` → `[0-9]+`                                 |
| `TEXTO`       → `"` [] `"`                               |
| `VARIAVEL`    → `[a-z][A-Z a-z]*`                        |-->

| 💠 **DECLARAÇÃO DE VARIÁVEIS** 💠                       |
|----------------------------------------------------------|
| `declaracao` → `tipoVar` `VARIAVEL` `valoravel` `;`      |
| `valoravel`  → `'='` `idt` \| ε                          |
| `tipoVar`    → `'Inteiro'` \| `'Decimal'` \| `'Texto'`   |
| `idt`        → `NUM_DECIMAL` \| `NUM_INTEIRO` \| `TEXTO` |
| 💠💠💠💠💠💠💠 **REGRAS LÉXICAS** 💠💠💠💠💠💠💠  |
| São: `idt` e `VARIAVEL`                                  |

<!--| 💠 **ATRIBUIÇÃO** 💠                                    |
|----------------------------------------------------------|
| `atribuicao` → `VARIAVEL` `'='` `valor`                  |
| `valor`      → `VARIAVEL` \| `idt`                       |
| `idt`        → `NUM_DECIMAL` \| `NUM_INTEIRO` \| `TEXTO` |
| 💠💠💠💠💠💠💠 **REGRAS LÉXICAS** 💠💠💠💠💠💠💠  |
| `NUM_DECIMAL` → `[0-9]+ '.' [0-9]+`                      |
| `NUM_INTEIRO` → `[0-9]+`                                 |
| `TEXTO`       → `"` [] `"`                               |
| `VARIAVEL`    → `[a-z][A-Z a-z]*`                        |-->

| 💠 **ATRIBUIÇÃO** 💠                                    |
|----------------------------------------------------------|
| `atribuicao` → `VARIAVEL` `'='` `valor`                  |
| `valor`      → `VARIAVEL` \| `idt`                       |
| `idt`        → `NUM_DECIMAL` \| `NUM_INTEIRO` \| `TEXTO` |
| 💠💠💠💠💠💠💠 **REGRAS LÉXICAS** 💠💠💠💠💠💠💠  |
| São: `idt` e `VARIAVEL`                                  |

| 💠 **INPUT** 💠                            |
|---------------------------------------------|
| `input` → `'Inserir'` `->` `VARIAVEL` `';'` |
| 💠💠💠💠 **REGRAS LÉXICAS** 💠💠💠💠    |
| São: `idt` e `VARIAVEL`                     |

<!--| 💠 **ESTRUTURA CONDICIONAL** 💠                                                      |
|---------------------------------------------------------------------------------------|
| `quest`     →`'Quest'` `'('` `requisito` `')'` `'{'` `sn` `'}'` `request`             |
| `requisito` → `VARIAVEL` `COMP_OP` `VARIAVEL` \| `idt`                                |
| `idt`       → `NUM_DECIMAL` \| `NUM_INTEIRO` \| `TEXTO`                               |
| `sn`        → `SiNo`                                                                  |
| `Si`        → `'Si'` `'{'` `bloco` `'}'`                                              |
| `No`        → `'No'` `'{'` `bloco` `'}'` \| ε                                         |
| `request`   → `'Request'` `'('` `requisito` `')'` `'{'` `sn` `'}'` `request` \| ε     |
| 💠💠💠💠💠💠💠💠💠💠💠💠💠 **REGRAS LÉXICAS** 💠💠💠💠💠💠💠💠💠💠💠💠💠 |
| `NUM_DECIMAL` → `[0-9]+ '.' [0-9]+`                                                   |
| `NUM_INTEIRO` → `[0-9]+`                                                              |
| `TEXTO`       → `"` texto `"`                                                         |
| `VARIAVEL`    → `[a-z][A-Z a-z]*`                                                     |
| `COMP_OP`     → `!=` \| `==` \| `>=` \| `<=` \| `<` \| `>`                            |-->

| 💠 **ESTRUTURA CONDICIONAL** 💠                                                  |
|-----------------------------------------------------------------------------------|
| `quest`     →`'Quest'` `'('` `requisito` `')'` `'{'` `sn` `'}'` `request`         |
| `requisito` → `VARIAVEL` `COMP_OP` `VARIAVEL` \| `idt`                            |
| `idt`       → `NUM_DECIMAL` \| `NUM_INTEIRO` \| `TEXTO`                           |
| `sn`        → `SiNo`                                                              |
| `Si`        → `'Si'` `'{'` `bloco` `'}'`                                          |
| `No`        → `'No'` `'{'` `bloco` `'}'` \| ε                                     |
| `request`   → `'Request'` `'('` `requisito` `')'` `'{'` `sn` `'}'` `request` \| ε |
| 💠💠💠💠💠💠💠💠💠💠💠💠 **REGRAS LÉXICAS** 💠💠💠💠💠💠💠💠💠💠💠💠  |
| São: `idt`, `VARIAVEL` e `COMP_OP`                                                |

| 💠 **QUEBRA** 💠|
|------------------|
|`'Quebra'` `';'`  |

| 💠 **LAÇO DE REPETIÇÃO** 💠                                                                    |
|-------------------------------------------------------------------------------------------------|
| `enlace`   → `'Enlace'` `'('` `rr` `')'` `'{'` `bloco` `'}'`                                    |
| `rr`       → `requisito` \| `'Roda'`                                                            |
| `ciclo`    → `'Ciclo'` `'('` `declaracao` `requisito` `';'` `atualiza` `')'` `'{'` `bloco` `'}'`|
| `atualiza` → `'Atualiza'` `'('` `VARIAVEL` `MATH_OP` `idt` `')'`                                |
| `idt`      → `NUM_DECIMAL` \| `NUM_INTEIRO` \| `TEXTO`                                          |
| 💠💠💠💠💠💠💠💠💠💠💠💠💠💠💠 **REGRAS LÉXICAS** 💠💠💠💠💠💠💠💠💠💠💠💠💠💠💠 |
| `VARIAVEL`    → `[a-z][A-Z a-z]*`                                                               |
| `MATH_OP`     → `+` \| `-` \| `*` \| `%` \| `/`                                                 |
| `NUM_DECIMAL` → `[0-9]+ '.' [0-9]+`                                                             |
| `NUM_INTEIRO` → `[0-9]+`                                                                        |
| `TEXTO`       → `"` [] `"`                                                                      |
| **Observações:** Regras como, `requisito`, `declaracao` já foram mostradas anteriormente        |

<!--| 💠 **FUNÇÃO** 💠                                                                               |
|-------------------------------------------------------------------------------------------------|
| `funcao`            → `'Destino'` `VARIAVEL` `'('` `parametros_funcao` `')'` `'{'` `bloco` `'}'`|
| `parametros_funcao` → `tipoVar` `VARIAVEL` `fim` \| ε                                           |
| `fim` → `','` `parametros_funcao` \| ε                                                          |
| `tipoVar`    → `'Inteiro'` \| `'Decimal'` \| `'Texto'`                                          |
| 💠💠💠💠💠💠💠💠💠💠💠💠💠💠💠 **REGRAS LÉXICAS** 💠💠💠💠💠💠💠💠💠💠💠💠💠💠💠 |
| `NUM_DECIMAL` → `[0-9]+ '.' [0-9]+`                                                             |
| `NUM_INTEIRO` → `[0-9]+`                                                                        |
| `TEXTO`       → `"` [] `"`                                                                      |-->


### 💠 Declaração de Função 💠  
```ebnf
funcao            → 'Destino' VARIAVEL '(' parametros_funcao ')' '{' bloco '}'
parametros_funcao → tipoVar VARIAVEL fim | ε
fim               → ',' parametros_funcao | ε
tipoVar           → 'Inteiro' | 'Decimal' | 'Texto'
```
💠 **Regras Léxicas:** `idt`

## :busts_in_silhouette: Desenvolvedores
| [<img loading="lazy" src="https://github.com/Mariah-Gomes/ProjetoCompMovel1/assets/141663285/e6827fd1-d8fe-4740-b6fc-fbbfccd05752" width=115><br><sub>Mariah Santos Gomes</sub>](https://github.com/Mariah-Gomes) | [<img loading="lazy" src="https://github.com/Mariah-Gomes/ProjetoCompMovel1/assets/141663285/66d7e656-b9e4-43b7-94fa-931b736df881" width=115><br><sub>Iago Rosa de Oliveira</sub>](https://github.com/iagorosa28) |
| :---: | :---: |
