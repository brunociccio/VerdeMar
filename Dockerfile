# Utilizando uma imagem base leve e otimizada para Java 17
FROM openjdk:17-slim

# Diretório de trabalho
WORKDIR /app

# Definição da versão do aplicativo
ARG JAR_FILE=target/verdemar-0.0.1-SNAPSHOT.jar

# Copiar o jar do aplicativo para o contêiner
COPY ${JAR_FILE} app.jar

# Criar o grupo e o usuário não root
RUN groupadd -r appgroup && useradd -r -g appgroup appuser

# Criar o diretório /app/data e ajustar permissões
RUN mkdir -p /app/data && chown -R appuser:appgroup /app

# Definir um usuário não root para rodar a aplicação
USER appuser

# Variável de ambiente
ENV APP_ENV=production

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]

# Rodar o container em background
CMD ["&"]