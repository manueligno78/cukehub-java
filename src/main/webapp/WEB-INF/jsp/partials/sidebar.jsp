<ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">
  <!-- Sidebar - Brand -->
  <a class="sidebar-brand d-flex align-items-center justify-content-center" href="/">
    <div class="sidebar-brand-icon rotate-n-15">
      <img src="TCM_Logo.png" alt="Logo" width="60" height="48">
    </div>
    <div class="sidebar-brand-text mx-3">Cuke<sup>HUB</sup></div>
  </a>

  <!-- Divider -->
  <hr class="sidebar-divider my-0">

  <!-- Nav Item - Feature -->
  <li class="nav-item active">
    <a class="nav-link" href="/">
      <i class="fas fa-fw fa-chart-area"></i>
      <span>Feature Explorer</span>
    </a>
  </li>

  <li class="nav-item flex-wrap">
    <div class="sidebar-heading m-1 p-1">Git : ${configuration.gitProjectUrl}
    </div>
  </li>

  <li class="nav-item flex-wrap">
    <div class="sidebar-heading m-1 p-1">Source path : ${configuration.directoryPath}
    </div>
  </li>

  <!-- Nav Item - Settings -->
  <li class="nav-item">
    <a class="nav-link" href="/settings">
      <i class="fas fa-fw fa-wrench"></i>
      <span>Settings</span></a>
  </li>

  <!-- Divider -->
  <hr class="sidebar-divider d-none d-md-block">

  <!-- Sidebar Toggler (Sidebar) -->
  <!-- TODO : On click should toggle sidebar to minimized view (only icons visible)-->
  <div class="text-center d-none d-md-inline">
    <button class="rounded-circle border-0" id="sidebarToggle"></button>
  </div>

  <!-- Divider -->
  <hr class="sidebar-divider d-none d-md-block">

  <!-- Nav Item - Save -->
  <li class="nav-item" id="saveButton" style="display: block;">
    <a class="nav-link" href="#" onclick="confirmExport()" title="Save , go to settings to change the export folder">
      <i class="fas fa-fw fa-save"></i>
      <span>Save</span></a>
  </li>

  <!-- Nav Item - Reset -->
  <li class="nav-item" id="resetButton" style="display: block;">
    <a class="nav-link" href="#" onclick="confirmReset()">
      <i class="fas fa-fw fa-undo"></i>
      <span>Reset</span></a>
  </li>
  <!-- Nav Item - GitStatus -->
  <li class="nav-item" style="display: block;">
    <!-- Show git status -->
    <div class="small" id="git-status">
      <!--jsp:include page="gitStatus.jsp" /-->
    </div>
  </li>
</ul>


<script>
  $(document).ready(function () {
    $('#sidebarToggle').on('click', function () {
      $('#accordionSidebar').toggleClass('toggled');
    });
  });
</script>