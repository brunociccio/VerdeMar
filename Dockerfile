# Utilizando uma imagem base leve e otimizada para Java
FROM openjdk:17-slim

# Diretório de trabalho
WORKDIR /app

# Argumento para definir a versão do aplicativo
ARG JAR_FILE=target/verdemar-0.0.1-SNAPSHOT.jar

# Copiar o jar do aplicativo para o contêiner
COPY ${JAR_FILE} app.jar

# Criar o grupo e o usuário não root
RUN groupadd -r appgroup && useradd -r -g appgroup appuser
USER appuser

# Variável de ambiente
ENV APP_ENV=production

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]

# Rodar o container em background
CMD ["&"]