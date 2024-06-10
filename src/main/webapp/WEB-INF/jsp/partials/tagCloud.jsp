<style>
  #tagCloud {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
  }
</style>

<%@ page import="java.util.*" %>
  <% Map<String, Map<String, Object>> tagFrequency = new HashMap<>();
      int maxFrequency = 1;
      // featureFiles should be a List of FeatureFile objects
      for (FeatureFile featureFile : featureFiles) {
      if (featureFile != null && featureFile.getPath() != null) {
      if (featureFile.getFeature().getTags() != null) {
      for (Tag tag : featureFile.getFeature().getTags()) {
      if (tagFrequency.containsKey(tag.getName())) {
      Map<String, Object> tagData = tagFrequency.get(tag.getName());
        tagData.put("count", (Integer) tagData.get("count") + 1);
        ((List<String>) tagData.get("features")).add(featureFile.getFeature().getName());
          if ((Integer) tagData.get("count") > maxFrequency) {
          maxFrequency = (Integer) tagData.get("count");
          }
          } else {
          Map<String, Object> tagData = new HashMap<>();
              tagData.put("count", 1);
              tagData.put("scenarios", new ArrayList<String>());
                tagData.put("features", Arrays.asList(featureFile.getFeature().getName()));
                tagFrequency.put(tag.getName(), tagData);
                }
                }
                }
                for (Child child : featureFile.getFeature().getChildren()) {
                if (child.getScenario() != null) {
                for (Tag tag : child.getScenario().getTags()) {
                if (tagFrequency.containsKey(tag.getName())) {
                Map<String, Object> tagData = tagFrequency.get(tag.getName());
                  tagData.put("count", (Integer) tagData.get("count") + 1);
                  ((List<String>) tagData.get("scenarios")).add(child.getScenario().getName());
                    if ((Integer) tagData.get("count") > maxFrequency) {
                    maxFrequency = (Integer) tagData.get("count");
                    }
                    } else {
                    Map<String, Object> tagData = new HashMap<>();
                        tagData.put("count", 1);
                        tagData.put("scenarios", Arrays.asList(child.getScenario().getName()));
                        tagData.put("features", new ArrayList<String>());
                          tagFrequency.put(tag.getName(), tagData);
                          }
                          }
                          }
                          }
                          }
                          }
                          %>

<div id="tagCloud" class="d-flex gap-2 justify-content-center flex-wrap">
  <!-- Tags -->
  <% int counter=0; %>
    <% for (Map.Entry<String, Map<String, Object>> entry : tagFrequency.entrySet()) {
      String tag = entry.getKey();
      Map<String, Object> tagData = entry.getValue();
        %>
        <span id="tag<%= counter %>" class="tag-label badge d-flex p-2 mr-1 mb-1 align-items-center text-primary-emphasis bg-primary-subtle border
          border-primary-subtle rounded-pill" style="font-size: <%= (1 + (((Integer)tagData.get(" count")) - 1) /
          (maxFrequency - 1))/1.5 %>em"
          title="<%= ((List<String>)tagData.get("scenarios")).size() > 0 ? 'This tag is present on: \n' +
            String.join("\n ", (List<String>)tagData.get("scenarios")) : 'This tag is present at the feature level in:
              \n' + String.join("\n ", (List<String>)tagData.get("features")) %>"
                data-count="<%= tagData.get("count") %>" onclick='addTagToDataTableSearchInput("<%= tag %>")'
                    data-name="<%= tag %>">
                      <span contenteditable="true" onblur='updateAllOccurencyOfTag("<%= tag %>", this.innerText)'
                        class="px-1">
                        <%= tag %>
                      </span>
                      <span class="badge badge-secondary badge-counter m-1">
                        <%= tagData.get("count") %>
                      </span>
                      <button type="button" class="close" aria-label="Close"
                        onclick='deleteAllOccurencyOfTag("<%= tag %>")'>
                        <span aria-hidden="true">×</span></button>
        </span>
        <% counter++; %>
          <% } %>
</div>
<span
  class="badge d-flex p-2 mr-1 mb-2 align-items-center text-primary-emphasis bg-primary-subtle border border-primary-subtle rounded-pill">
  <span class="px-1"><input type="checkbox" id="showAllTags" onclick="sortAndShowTags()">Show all</input>
  </span>
</span>
<span id="sortCheckboxContainer"
  class="badge p-2 mr-1 mb-2 align-items-center text-primary-emphasis bg-primary-subtle border border-primary-subtle rounded-pill">
  <label for="sortCheckbox" id="sortCheckboxLabel">Switch to alphabetical sorting</label>
  <input type="checkbox" id="sortCheckbox" onclick="sortAndShowTags()" checked>
</span>
<script src="/tagCloud.js"></script>