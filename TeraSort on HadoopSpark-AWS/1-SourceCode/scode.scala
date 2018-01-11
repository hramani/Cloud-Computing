val lines = sc.textFile("/root/input.txt")
val t1 = System.nanoTime
val sort = lines.map(_.split("  ")).map(arr => (arr(0) + "  " + arr(1),arr.mkString("  "))).sortByKey().map(_._2)
val duration = (System.nanoTime - t1) / 1e9d
println("Sorting time :")
println(duration)
sort.saveAsTextFile("/root/output")
val duration1 = (System.nanoTime - t1) / 1e9d
println("Total time Split, sort, merge and transfer to local:")
println(duration1)
