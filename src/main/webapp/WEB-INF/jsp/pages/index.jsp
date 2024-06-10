<!DOCTYPE html>
<html lang="it">

<head>
  <title>CukeHub</title>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">
  <!-- Custom fonts for this template -->
  <link href="/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link
    href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
    rel="stylesheet">

  <!-- Custom styles for this template -->
  <link href="/css/sb-admin-2.min.css" rel="stylesheet">

  <!-- Bootstrap CSS -->
  <link href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.2/css/bootstrap.css" rel="stylesheet">
  <link href="https://cdn.datatables.net/2.0.7/css/dataTables.bootstrap4.css" rel="stylesheet">

  <!-- jQuery -->
  <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
    integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
    crossorigin="anonymous"></script>

  <!-- Bootstrap JavaScript -->
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.2/js/bootstrap.min.js"></script>
  <script src="https://cdn.datatables.net/2.0.7/js/dataTables.js"></script>
  <script src="https://cdn.datatables.net/2.0.7/js/dataTables.bootstrap4.js"></script>

  <script src="/main.js"></script>
  <link href="/main.css" rel="stylesheet">
  <!-- Favicon -->
  <link rel="icon" href="favicon-32x32.png">
</head>

<body id="page-top">
  <div id="wrapper">
    <!-- Sidebar -->
    <div id="sidebar-wrapper">
      <jsp:include page="../partials/sidebar.jsp" />
        <!-- End of Sidebar -->
    </div>
    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">
      <!-- Main Content -->
      <div id="content">
        <nav class="navbar navbar-expand navbar-light bg-white mb-4 static-top shadow">
          <!-- Tags cloud-->
          <div class="container-fluid d-flex flex-wrap">
            <!--jsp:include page="../partials/tagCloud.jsp" /-->
          </div>
        </nav>
        <!-- Begin Page Content -->
        <div class="container-fluid">
          <!-- Page Heading -->
          <div class="card shadow mb-4">
            <div class="card-body">
              <div class="table-responsive">

                <div id="dataTable_wrapper" class="dataTables_wrapper dt-bootstrap4">
                  <table id="dataTable" class="table table-striped display">
                    <thead>
                      <tr>
                        <th scope="col">Feature</th>
                        <th scope="col">Scenarios</th>
                      </tr>
                    </thead>
                    <tbody>
                      <!--jsp:include page="../partials/featureFileList.jsp" /-->
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <footer class="sticky-footer bg-white">
        <jsp:include page="../partials/footer.jsp" />
      </footer>
      <!-- End of Content Wrapper -->
    </div>
    <!-- End of Page Wrapper -->
</body>

</html>
<svg xmlns="http://www.w3.org/2000/svg" style="display: none;">
  <symbol id="x-circle-fill" viewBox="0 0 16 16">
    <path
      d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zM5.354 4.646a.5.5 0 1 0-.708.708L7.293 8l-2.647 2.646a.5.5 0 0 0 .708.708L8 8.707l2.646 2.647a.5.5 0 0 0 .708-.708L8.707 8l2.647-2.646a.5.5 0 0 0-.708-.708L8 7.293 5.354 4.646z"
      style="fill: #858796;"></path>
  </symbol>
</svg>