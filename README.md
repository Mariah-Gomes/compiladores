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
| Token          | Exemplo       |
|----------------|---------------|
| **NUM_DECIMAL**| `6.6`         |
| **NUM_INTEIRO**| `6`           |
| **TEXTO**      | `"Mariago"`   |

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
| Token         | Exemplo |
|---------------|---------|
| **VARIAVEL**  | `aA`    |

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
| Token           | Exemplo     |
|-----------------|-------------|
| **COMENTARIO**  | `_texto_`   |

### 🔑 Palavras Reservadas
#### 🧠 Tipos de Variável
| Token         | Palavra        |
|---------------|----------------|
| **RESERVADA** | `Inteiro`      |
| **RESERVADA** | `Decimal`      |
| **RESERVADA** | `Texto`        |

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

## Analisador Sintático
### Declaração de Tipo:
TV → tipoVar VARIAVEL '=' idt ';'
tipoVar → 'Inteiro' | 'Decimal' | 'Texto'
idt → NUM_DECIMAL | NUM_INTEIRO | TEXTO
NUM_DECIMAL → [0-9]⁺ '.' [0-9]⁺
NUM_INTEIRO → [0-9]⁺
TEXTO → '"' texto '"'

## :busts_in_silhouette: Desenvolvedores
| [<img loading="lazy" src="https://github.com/Mariah-Gomes/ProjetoCompMovel1/assets/141663285/e6827fd1-d8fe-4740-b6fc-fbbfccd05752" width=115><br><sub>Mariah Santos Gomes</sub>](https://github.com/Mariah-Gomes) | [<img loading="lazy" src="https://github.com/Mariah-Gomes/ProjetoCompMovel1/assets/141663285/66d7e656-b9e4-43b7-94fa-931b736df881" width=115><br><sub>Iago Rosa de Oliveira</sub>](https://github.com/iagorosa28) |
| :---: | :---: |
