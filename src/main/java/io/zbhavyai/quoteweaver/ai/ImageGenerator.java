package io.zbhavyai.quoteweaver.ai;

import dev.langchain4j.data.image.Image;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.zbhavyai.quoteweaver.client.GeminiImageClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class ImageGenerator {

  private final GeminiImageClient _imageClient;
  private final String _modelId;

  @Inject
  public ImageGenerator(
      @RestClient GeminiImageClient imageClient,
      @ConfigProperty(name = "gemini.image-model-id") String modelId) {

    _imageClient = imageClient;
    _modelId = modelId;
  }

  public Image generateImage(String quoteText) {
    return _imageClient
        .generateImage(_modelId, createImagePrompt(quoteText))
        .map(this::mapToImage)
        .await()
        .indefinitely();
  }

  private Image mapToImage(JsonObject json) {

    try {
      Image.Builder image = Image.builder();

      JsonArray generatedParts =
          json.getJsonArray("candidates")
              .getJsonObject(0)
              .getJsonObject("content")
              .getJsonArray("parts");

      for (int i = 0; i < generatedParts.size(); i++) {
        JsonObject part = generatedParts.getJsonObject(i);

        if (part.containsKey("inlineData")) {
          image.mimeType(part.getJsonObject("inlineData").getString("mimeType"));
          image.base64Data(part.getJsonObject("inlineData").getString("data"));
          break;
        }

        if (part.containsKey("text")) {
          image.revisedPrompt(part.getString("text"));
        }
      }

      return image.build();
    } catch (Exception e) {
      throw new RuntimeException("Failed to parse image response: " + json.encodePrettily(), e);
    }
  }

  private JsonObject createImagePrompt(String quoteText) {
    return new JsonObject()
        .put(
            "system_instruction",
            new JsonObject()
                .put("parts", new JsonArray().add(new JsonObject().put("text", getSystemPrompt()))))
        .put(
            "contents",
            new JsonArray()
                .add(
                    new JsonObject()
                        .put(
                            "parts",
                            new JsonArray()
                                .add(new JsonObject().put("text", getUserPrompt(quoteText))))));
  }

  private String getSystemPrompt() {
    return """
    You are an expert AI image generator. Your task is to create artistic and metaphorical images that capture the essence of a given quote.

    Do NOT include the text of the quote in the image.

    The image should not be a literal depiction of the quote's text, but rather an abstract or symbolic representation that conveys the underlying emotion and meaning.
    The style should be modern and visually appealing, suitable for sharing on social media platforms.

    Ensure the generated image is high quality and visually engaging.
    """;
  }

  private String getUserPrompt(String quoteText) {
    return String.format(
        """
        Generate an image that visually represents the following quote:
        "%s"
        """,
        quoteText);
  }
}
